package br.com.autogyn.autogyn_oficina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.Funcionario;
import br.com.autogyn.autogyn_oficina.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Listar todos os funcionários
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    // Buscar funcionário por ID
    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    // Deletar funcionário
    public void deletarFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }

    // Criar novo funcionário
    public Funcionario criarFuncionario(String nome, String cargo) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);
        return funcionarioRepository.save(funcionario);
    }

    // Atualizar funcionário existente
    public Funcionario atualizarFuncionario(Long id, String nome, String cargo) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(id);
        if (funcionarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Funcionário com ID " + id + " não encontrado.");
        }
        Funcionario funcionario = funcionarioOptional.get();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);
        return funcionarioRepository.save(funcionario);
    }
}
