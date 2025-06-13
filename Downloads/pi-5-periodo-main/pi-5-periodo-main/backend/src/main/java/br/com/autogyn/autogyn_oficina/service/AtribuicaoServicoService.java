package br.com.autogyn.autogyn_oficina.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.AtribuicaoServico;
import br.com.autogyn.autogyn_oficina.entity.Funcionario;
import br.com.autogyn.autogyn_oficina.entity.Servico;
import br.com.autogyn.autogyn_oficina.repository.AtribuicaoServicoRepository;
import br.com.autogyn.autogyn_oficina.repository.FuncionarioRepository;
import br.com.autogyn.autogyn_oficina.repository.ServicoRepository;

@Service
public class AtribuicaoServicoService {

    @Autowired
    private AtribuicaoServicoRepository atribuicaoServicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public List<AtribuicaoServico> listar() {
        return atribuicaoServicoRepository.findAll();
    }

    public AtribuicaoServico buscarPorId(Long id) {
        return atribuicaoServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atribuição com ID " + id + " não encontrada."));
    }

    public void atribuirServico(Long servicoId, Long funcionarioId, String descricao) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço com ID " + servicoId + " não encontrado."));

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário com ID " + funcionarioId + " não encontrado."));

        AtribuicaoServico atribuicao = new AtribuicaoServico();
        atribuicao.setServico(servico);
        atribuicao.setFuncionario(funcionario);
        atribuicao.setDescricao(descricao);
        atribuicao.setDuracaoHora(LocalDateTime.now());

        atribuicaoServicoRepository.save(atribuicao);
    }

    public void atualizar(Long atribuicaoId, Long novoServicoId, Long novoFuncionarioId, String novaDescricao) {
        AtribuicaoServico atribuicaoExistente = atribuicaoServicoRepository.findById(atribuicaoId)
                .orElseThrow(() -> new RuntimeException("Atribuição com ID " + atribuicaoId + " não encontrada."));

        Servico novoServico = servicoRepository.findById(novoServicoId)
                .orElseThrow(() -> new RuntimeException("Serviço com ID " + novoServicoId + " não encontrado."));

        Funcionario novoFuncionario = funcionarioRepository.findById(novoFuncionarioId)
                .orElseThrow(
                        () -> new RuntimeException("Funcionário com ID " + novoFuncionarioId + " não encontrado."));

        atribuicaoExistente.setServico(novoServico);
        atribuicaoExistente.setFuncionario(novoFuncionario);
        atribuicaoExistente.setDescricao(novaDescricao);
        atribuicaoExistente.setDuracaoHora(LocalDateTime.now());

        atribuicaoServicoRepository.save(atribuicaoExistente);
    }

    public void deletar(Long atribuicaoId) {
        AtribuicaoServico atribuicao = atribuicaoServicoRepository.findById(atribuicaoId)
                .orElseThrow(() -> new RuntimeException("Atribuição com ID " + atribuicaoId + " não encontrada."));

        atribuicaoServicoRepository.delete(atribuicao);
    }
}
