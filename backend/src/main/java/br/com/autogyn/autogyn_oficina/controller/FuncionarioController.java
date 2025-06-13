package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autogyn.autogyn_oficina.dto.FuncionarioDTO; // import do DTO
import br.com.autogyn.autogyn_oficina.entity.Funcionario;
import br.com.autogyn.autogyn_oficina.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // Listar todos os funcionários
    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioService.listarTodos();
    }

    // Buscar funcionário por ID
    @GetMapping("/{id}")
    public Optional<Funcionario> buscarPorID(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id);
    }

    // Criar novo funcionário
    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario novoFuncionario = funcionarioService.criarFuncionario(funcionarioDTO.getNome(),
                funcionarioDTO.getCargo());
        return ResponseEntity.ok(novoFuncionario);
    }

    // Atualizar funcionário
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(id, funcionarioDTO.getNome(),
                    funcionarioDTO.getCargo());
            return ResponseEntity.ok(funcionarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}
