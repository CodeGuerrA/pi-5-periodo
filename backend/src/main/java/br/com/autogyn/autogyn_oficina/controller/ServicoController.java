package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autogyn.autogyn_oficina.dto.ServicoDTO;
import br.com.autogyn.autogyn_oficina.entity.Servico;
import br.com.autogyn.autogyn_oficina.service.ServicoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/servicos")
public class ServicoController {
    // fazendo a injeção de dependencia para nao precisar criar os controllers
    @Autowired
    private ServicoService service;

    // Metodo post para criar Serviços
    @PostMapping
    public ResponseEntity<Servico> criarServico(@RequestBody ServicoDTO servicoDTO) {

        try {

            Servico servico = service.criarServico(servicoDTO.getNome(), servicoDTO.getValor());
            return ResponseEntity.status(HttpStatus.CREATED).body(servico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    // Metodo para listar todos os serviços
    @GetMapping
    public List<Servico> listar() {
        return service.listarTodos();
    }

    // Metodo para deletar um serviço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletarServico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Optional<Servico> buscarporID(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizarServico(@PathVariable Long id, @RequestBody ServicoDTO servicoDTO) {

        try {
            Servico servicoAtualizar = service.atualizarServico(id, servicoDTO.getNome(), servicoDTO.getValor());
            return ResponseEntity.ok(servicoAtualizar);
        } catch (IllegalArgumentException e) {
            // Pode ser documento inválido ou tipo de cliente incompatível
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // Caso o cliente não exista, pode retornar 404
            return ResponseEntity.notFound().build();
        }
    }

}
