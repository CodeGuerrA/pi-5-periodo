package br.com.autogyn.autogyn_oficina.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.autogyn.autogyn_oficina.entity.Cliente;
import br.com.autogyn.autogyn_oficina.entity.ItemPecaOS;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.entity.Pagamento;
import br.com.autogyn.autogyn_oficina.entity.Servico;
import br.com.autogyn.autogyn_oficina.entity.Veiculo;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;
import br.com.autogyn.autogyn_oficina.enums.StatusPagamento;
import br.com.autogyn.autogyn_oficina.observer.EmailObserver;
import br.com.autogyn.autogyn_oficina.observer.LogObserver;
import br.com.autogyn.autogyn_oficina.observer.OrdemServicoSubject;
import br.com.autogyn.autogyn_oficina.observer.PainelObserver;
import br.com.autogyn.autogyn_oficina.repository.ClienteRepository;
import br.com.autogyn.autogyn_oficina.repository.ItemPecaOSRepository;
import br.com.autogyn.autogyn_oficina.repository.OrdemServicoRepository;
import br.com.autogyn.autogyn_oficina.repository.ServicoRepository;
import br.com.autogyn.autogyn_oficina.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class OrdemServicoService {

        @Autowired
        private OrdemServicoRepository ordemServicoRepository;

        @Autowired
        private ClienteRepository clienteRepository;

        @Autowired
        private VeiculoRepository veiculoRepository;

        @Autowired
        private ItemPecaOSRepository itemPecaOSRepository;

        @Autowired
        private ItemPecaOsService itemPecaOsService;

        @Autowired
        private PagamentoService pagamentoService;

        @Autowired
        private ServicoRepository servicoRepository;

        public List<OrdemServico> listarTodas() {
                return ordemServicoRepository.findAll();
        }

        public Optional<OrdemServico> buscarPorId(Long id) {
                return ordemServicoRepository.findById(id);
        }

        // Metodo para criar OS
        @Transactional
        public OrdemServico criar(OrdemServico ordemServico, Long clienteId, Long veiculoId,
                        List<Long> itemPecaIds, List<Integer> quantidadePecas, List<Long> servicoIds) {
                Cliente cliente = clienteRepository.findById(clienteId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cliente não encontrado com ID: " + clienteId));
                Veiculo veiculo = veiculoRepository.findById(veiculoId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Veículo não encontrado com ID: " + veiculoId));

                boolean veiculoEmUso = ordemServicoRepository.existsByVeiculoIdAndStatusIn(
                                veiculoId, List.of(StatusOrdem.ABERTA));
                if (veiculoEmUso) {
                        throw new IllegalStateException(
                                        "Este veículo já está vinculado a uma ordem de serviço em andamento.");
                }

                ordemServico.setCliente(cliente);
                ordemServico.setVeiculo(veiculo);
                ordemServico.setServicos(new ArrayList<>());
                for (Long servicoId : servicoIds) {
                        Servico servico = servicoRepository.findById(servicoId)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "Serviço não encontrado com ID: " + servicoId));
                        ordemServico.getServicos().add(servico);
                }
                ordemServico.setDataAbertura(LocalDateTime.now());
                ordemServico.setStatus(StatusOrdem.ABERTA);

                ordemServico.setItensPeca(new ArrayList<>());
                for (int i = 0; i < itemPecaIds.size(); i++) {
                        ItemPecaOS itemPeca = itemPecaOsService.criarItem(itemPecaIds.get(i), quantidadePecas.get(i));
                        itemPeca.setOrdemServico(ordemServico);
                        ordemServico.getItensPeca().add(itemPeca);
                }

                return ordemServicoRepository.save(ordemServico);
        }

        // Metodo para atualizarOS
        @Transactional
        public OrdemServico atualizaOrdemServico(Long id, OrdemServico ordemServicoAtualizada, Long clienteId,
                        Long veiculoId, List<Long> itemPecaIds, List<Integer> quantidadePecas, List<Long> servicoIds) {
                OrdemServico ordemExistente = ordemServicoRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Ordem de Serviço com ID " + id + " não encontrada."));

                Cliente cliente = clienteRepository.findById(clienteId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cliente não encontrado com ID: " + clienteId));
                Veiculo veiculo = veiculoRepository.findById(veiculoId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Veículo não encontrado com ID: " + veiculoId));

                ordemExistente.setCliente(cliente);
                ordemExistente.setVeiculo(veiculo);
                ordemExistente.setServicos(new ArrayList<>());
                for (Long servicoId : servicoIds) {
                        Servico servico = servicoRepository.findById(servicoId)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "Serviço não encontrado com ID: " + servicoId));
                        ordemExistente.getServicos().add(servico);
                }
                ordemExistente.setDataAbertura(LocalDateTime.now());
                ordemExistente.setStatus(
                                ordemServicoAtualizada.getStatus() != null ? ordemServicoAtualizada.getStatus()
                                                : StatusOrdem.ABERTA);

                OrdemServicoSubject subject = new OrdemServicoSubject();
                subject.adicionarObservador(new PainelObserver());
                subject.adicionarObservador(new EmailObserver());
                subject.adicionarObservador(new LogObserver());
                subject.notificarTodos(
                                "Status da OS #" + id + " atualizado para: " + ordemExistente.getStatus());

                if (ordemExistente.getItensPeca() != null) {
                        for (ItemPecaOS item : ordemExistente.getItensPeca()) {
                                itemPecaOSRepository.delete(item);
                        }
                }
                ordemExistente.getItensPeca().clear();

                List<ItemPecaOS> novosItens = new ArrayList<>();
                for (int i = 0; i < itemPecaIds.size(); i++) {
                        ItemPecaOS item = itemPecaOsService.criarItem(itemPecaIds.get(i), quantidadePecas.get(i));
                        novosItens.add(item);
                }
                ordemExistente.setItensPeca(novosItens);

                return ordemServicoRepository.save(ordemExistente);
        }

        // Deletar OS
        @Transactional
        public void deletarOrdemServico(Long id) {
                ordemServicoRepository.deleteById(id);
        }

        // FinalizarOS
        @Transactional
        public void finalizarOrdemServico(Long id, String formaPagamento) {
                OrdemServico ordemServico = ordemServicoRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Ordem de Serviço com ID " + id + " não encontrada."));

                // 1) Soma das peças
                BigDecimal valorTotalPecas = ordemServico.getItensPeca().stream()
                                .map(item -> item.getPrecoFinal() != null
                                                ? item.getPrecoFinal()
                                                : BigDecimal.ZERO)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 2) Soma dos serviços
                BigDecimal valorTotalServicos = ordemServico.getServicos().stream()
                                .map(s -> s.getValor() != null
                                                ? s.getValor()
                                                : BigDecimal.ZERO)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 3) Total geral (peças + serviços)
                BigDecimal valorTotal = valorTotalPecas.add(valorTotalServicos);

                // Cria e processa o pagamento com o total correto
                Pagamento pagamento = new Pagamento(
                                valorTotal,
                                formaPagamento,
                                LocalDate.now(),
                                StatusPagamento.PAGO);
                pagamentoService.processarPagamento(pagamento);

                // Atualiza a OS
                ordemServico.setStatus(StatusOrdem.FINALIZADA);
                ordemServico.setPagamento(pagamento);
                ordemServico.setDataFechamento(LocalDateTime.now());

                ordemServicoRepository.save(ordemServico);
        }

        // Gerar relatorio com Jasper
        public byte[] gerarRelatorioFinanceiro(Long id) {
                OrdemServico os = ordemServicoRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Ordem de Serviço não encontrada."));

                if (os.getStatus() != StatusOrdem.FINALIZADA) {
                        throw new IllegalStateException(
                                        "Relatório só pode ser gerado para ordens FINALIZADAS.");
                }

                try {
                        InputStream template = getClass()
                                        .getResourceAsStream("/relatorios/relatorio_ordem_servico.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(template);

                        Map<String, Object> parametros = new HashMap<>();
                        parametros.put("cliente", os.getCliente().getNome());
                        parametros.put("veiculo", os.getVeiculo().getModelo());
                        parametros.put("servicos", os.getServicos()
                                        .stream().map(Servico::getNome).collect(Collectors.joining(", ")));
                        parametros.put("dataAbertura", os.getDataAbertura().toLocalDate().toString());
                        parametros.put("dataFechamento", os.getDataFechamento().toLocalDate().toString());
                        parametros.put("formaPagamento", os.getPagamento().getFormaPagamento());
                        parametros.put("valorTotal", os.getPagamento().getValorTotal());

                        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(os.getItensPeca());
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

                        return JasperExportManager.exportReportToPdf(jasperPrint);

                } catch (Exception e) {
                        throw new RuntimeException("Erro ao gerar relatório: " + e.getMessage(), e);
                }
        }
}
