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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.autogyn.autogyn_oficina.entity.Cliente;
import br.com.autogyn.autogyn_oficina.entity.Funcionario;
import br.com.autogyn.autogyn_oficina.entity.ItemPecaOS;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.entity.Pagamento;
import br.com.autogyn.autogyn_oficina.entity.Veiculo;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;
import br.com.autogyn.autogyn_oficina.enums.StatusPagamento;
import br.com.autogyn.autogyn_oficina.observer.EmailObserver;
import br.com.autogyn.autogyn_oficina.observer.LogObserver;
import br.com.autogyn.autogyn_oficina.observer.OrdemServicoSubject;
import br.com.autogyn.autogyn_oficina.observer.PainelObserver;
import br.com.autogyn.autogyn_oficina.repository.ClienteRepository;
import br.com.autogyn.autogyn_oficina.repository.FuncionarioRepository;
import br.com.autogyn.autogyn_oficina.repository.ItemPecaOSRepository;
import br.com.autogyn.autogyn_oficina.repository.OrdemServicoRepository;
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
        private FuncionarioRepository funcionarioRepository;

        @Autowired
        private ItemPecaOsService itemPecaOsService;

        @Autowired
        private PagamentoService pagamentoService;

        public List<OrdemServico> listarTodas() {
                return ordemServicoRepository.findAll();
        }

        public Optional<OrdemServico> buscarPorId(Long id) {
                return ordemServicoRepository.findById(id);
        }

        @Transactional
        public OrdemServico criar(OrdemServico ordemServico, Long clienteId, Long veiculoId, List<Long> itemPecaIds,
                        List<Integer> quantidadePecas, Long funcionarioId) {
                // Buscar cliente
                Cliente cliente = clienteRepository.findById(clienteId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cliente não encontrado com ID: " + clienteId));

                // Buscar veículo
                Veiculo veiculo = veiculoRepository.findById(veiculoId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Veículo não encontrado com ID: " + veiculoId));

                // Verificar se o veículo já está vinculado a outra ordem em aberto
                boolean veiculoEmUso = ordemServicoRepository.existsByVeiculoIdAndStatusIn(
                                veiculoId,
                                List.of(StatusOrdem.ABERTA));
                if (veiculoEmUso) {
                        throw new IllegalStateException(
                                        "Este veículo já está vinculado a uma ordem de serviço em andamento.");
                }

                // Buscar funcionário
                Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Funcionário não encontrado com ID: " + funcionarioId));

                // Preencher dados da OS
                ordemServico.setCliente(cliente);
                ordemServico.setVeiculo(veiculo);
                ordemServico.setFuncionario(funcionario);
                ordemServico.setDataAbertura(LocalDateTime.now());
                ordemServico.setStatus(StatusOrdem.ABERTA);

                // Associar peças
                ordemServico.setItensPeca(new ArrayList<>());
                for (int i = 0; i < itemPecaIds.size(); i++) {
                        ItemPecaOS itemPeca = itemPecaOsService.criarItem(itemPecaIds.get(i), quantidadePecas.get(i));
                        itemPeca.setOrdemServico(ordemServico);
                        ordemServico.getItensPeca().add(itemPeca);
                }

                return ordemServicoRepository.save(ordemServico);
        }

        @Transactional
        public OrdemServico atualizaOrdemServico(long id, OrdemServico ordemServicoAtualizada, Long clienteId,
                        Long veiculoId,
                        List<Long> itemPecaIds, List<Integer> quantidadePecas, Long funcionarioId) {
                OrdemServico ordemExistente = ordemServicoRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Ordem de Serviço com ID " + id + " não encontrada."));

                // Buscar cliente, veículo e funcionário
                Cliente cliente = clienteRepository.findById(clienteId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Cliente não encontrado com ID: " + clienteId));
                Veiculo veiculo = veiculoRepository.findById(veiculoId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Veículo não encontrado com ID: " + veiculoId));
                Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Funcionário não encontrado com ID: " + funcionarioId));

                // Atualizar dados da OS existente com os valores recebidos
                ordemExistente.setCliente(cliente);
                ordemExistente.setVeiculo(veiculo);
                ordemExistente.setFuncionario(funcionario);
                ordemExistente.setDataAbertura(LocalDateTime.now());
                ordemExistente.setStatus(ordemServicoAtualizada.getStatus() != null ? ordemServicoAtualizada.getStatus()
                                : StatusOrdem.ABERTA);

                // Notificação Observer
                OrdemServicoSubject subject = new OrdemServicoSubject();
                subject.adicionarObservador(new PainelObserver());
                subject.adicionarObservador(new EmailObserver());
                subject.adicionarObservador(new LogObserver());
                subject.notificarTodos("Status da OS #" + id + " atualizado para: " + ordemExistente.getStatus());

                // Remover os itens antigos (se necessário)
                if (ordemExistente.getItensPeca() != null) {
                        for (ItemPecaOS item : ordemExistente.getItensPeca()) {
                                itemPecaOSRepository.delete(item); // remove os itens antigos do banco
                        }
                }
                ordemExistente.getItensPeca().clear();

                // Criar novos itens e associar à OS
                List<ItemPecaOS> novosItens = new ArrayList<>();
                for (int i = 0; i < itemPecaIds.size(); i++) {
                        ItemPecaOS item = itemPecaOsService.criarItem(itemPecaIds.get(i), quantidadePecas.get(i));
                        novosItens.add(item);
                }
                ordemExistente.setItensPeca(novosItens);

                return ordemServicoRepository.save(ordemExistente);
        }

        @Transactional
        public void deletarOrdemServico(Long id) {
                ordemServicoRepository.deleteById(id);
        }

        @Transactional
        public void finalizarOrdemServico(Long id, String formaPagamento) {
                Optional<OrdemServico> ordemServicoOptional = ordemServicoRepository.findById(id);
                if (ordemServicoOptional.isEmpty()) {
                        throw new IllegalArgumentException("Ordem de Serviço com ID " + id + "não encontrada.");
                }
                OrdemServico ordemServico = ordemServicoOptional.get();
                BigDecimal valorTotal = BigDecimal.ZERO;

                for (ItemPecaOS itensPeca : ordemServico.getItensPeca()) {
                        valorTotal = valorTotal.add(itensPeca.getPrecoFinal() != null ? itensPeca.getPrecoFinal()
                                        : BigDecimal.ZERO);
                }

                Pagamento pagamento = new Pagamento(valorTotal, formaPagamento, LocalDate.now(), StatusPagamento.PAGO);
                pagamentoService.processarPagamento(pagamento);

                ordemServico.setStatus(StatusOrdem.FINALIZADA);
                ordemServico.setPagamento(pagamento);
                ordemServico.setDataFechamento(LocalDateTime.now());

                ordemServicoRepository.save(ordemServico);
        }

        public byte[] gerarRelatorioFinanceiro(Long id) {
                OrdemServico os = ordemServicoRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Ordem de Serviço não encontrada."));

                if (os.getStatus() != StatusOrdem.FINALIZADA) {
                        throw new IllegalStateException("Relatório só pode ser gerado para ordens FINALIZADAS.");
                }

                try {
                        InputStream template = getClass()
                                        .getResourceAsStream("/relatorios/relatorio_ordem_servico.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(template);

                        Map<String, Object> parametros = new HashMap<>();
                        parametros.put("cliente", os.getCliente().getNome());
                        parametros.put("veiculo", os.getVeiculo().getModelo());
                        parametros.put("funcionario", os.getFuncionario().getNome());
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
