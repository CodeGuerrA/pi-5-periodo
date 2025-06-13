package br.com.autogyn.autogyn_oficina.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.autogyn.autogyn_oficina.entity.Cliente;
import br.com.autogyn.autogyn_oficina.entity.Funcionario;
import br.com.autogyn.autogyn_oficina.entity.ItemPecaOS;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.entity.Veiculo;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;
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

    public List<OrdemServico> listarTodas() {
        return ordemServicoRepository.findAll();
    }

    public OrdemServico buscarPorId(Long id) {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada com ID: " + id));
    }

    @Transactional
    public OrdemServico criar(OrdemServico ordemServico, Long clienteId, Long veiculoId, List<Long> itemPecaIds,
            List<Integer> quantidadePecas,
            Long funcionarioId) {
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + clienteId));

        // Buscar veículo
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));

        // Verificar se o veículo já está vinculado a outra ordem em aberto/em andamento
        boolean veiculoEmUso = ordemServicoRepository.existsByVeiculoIdAndStatusIn(
                veiculoId,
                List.of(StatusOrdem.ABERTA));

        if (veiculoEmUso) {
            throw new IllegalStateException("Este veículo já está vinculado a uma ordem de serviço em andamento.");
        }

        // Buscar funcionário
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        // Associar dados
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
    public OrdemServico atualizaOrdemServico(long id, OrdemServico ordemServico, Long clienteId, Long veiculoId,
            List<Long> itemPecaIds, Long funcionarioId) {
        Optional<OrdemServico> ordemServicoOptional = ordemServicoRepository.findById(id);
        if (ordemServicoOptional.isEmpty()) {
            throw new IllegalArgumentException("Ordem de Serviço com ID " + id + "não encontrada.");
        }
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com ID: " + clienteId));

        // Buscar veículo
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));

        // Buscar funcionário
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        // Associar dados
        ordemServico.setCliente(cliente);
        ordemServico.setVeiculo(veiculo);
        ordemServico.setFuncionario(funcionario);
        ordemServico.setDataAbertura(LocalDateTime.now());
        ordemServico.setStatus(StatusOrdem.ABERTA);

        // Padrão de Projeto: Observer
        OrdemServicoSubject subject = new OrdemServicoSubject();
        subject.adicionarObservador(new PainelObserver());
        subject.adicionarObservador(new EmailObserver());
        subject.adicionarObservador(new LogObserver());

        subject.notificarTodos("Status da OS #" + id + " atualizado para: " + ordemServico.getStatus());


        // Associar peças
        ordemServico.setItensPeca(new ArrayList<>());
        for (Long itemId : itemPecaIds) {
            ItemPecaOS itemPeca = itemPecaOSRepository.findById(itemId)
                    .orElseThrow(() -> new EntityNotFoundException("ItemPecaOS não encontrado com ID: " + itemId));
            itemPeca.setOrdemServico(ordemServico);
            ordemServico.getItensPeca().add(itemPeca);
        }

        return ordemServicoRepository.save(ordemServico);
    }
}
