package br.com.autogyn.autogyn_oficina.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.Servico;
import br.com.autogyn.autogyn_oficina.repository.ServicoRepository;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    // Metodo para criar um serviço
    public Servico criarServico(String nome, BigDecimal valor) {
        Servico servico = new Servico();

        servico.setNome(nome);
        servico.setValor(valor);
        return servicoRepository.save(servico);

    }

    // Metodo get para retornar todos os serviços
    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    // Buscar serviço por id
    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    // Metodo deletar
    public void deletarServico(Long id) {
        servicoRepository.deleteById(id);
    }

    // Metodo atualizar Serviço
    public Servico atualizarServico(Long id, String nome, BigDecimal valor) {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isEmpty()) {
            throw new IllegalArgumentException("Serviço com ID " + id + " não encontrado.");
        }

        Servico servico = servicoOptional.get();
        servico.setNome(nome);
        servico.setValor(valor);
        return servicoRepository.save(servico);
    }
}
