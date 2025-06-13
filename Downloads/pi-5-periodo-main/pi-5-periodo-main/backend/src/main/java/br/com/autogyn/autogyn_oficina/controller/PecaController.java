package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autogyn.autogyn_oficina.dto.PecaDTO;
import br.com.autogyn.autogyn_oficina.entity.Peca;
import br.com.autogyn.autogyn_oficina.service.PecasService;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    @Autowired
    PecasService pecasService;

    @PostMapping
    public ResponseEntity<Peca> criarPeca(@RequestBody PecaDTO pecaDTO) {
        System.out.println("Recebido: " + pecaDTO.getCodigo());
        System.out.println("Recebido: " + pecaDTO.getDescricao());
        System.out.println("Recebido: " + pecaDTO.getQuantidadeEstoque());
        System.out.println("Recebido: " + pecaDTO.getPrecoUnitario());
        try {
            Peca peca = pecasService.criarPeca(pecaDTO.getCodigo(), pecaDTO.getDescricao(), pecaDTO.getPrecoUnitario(),
                    pecaDTO.getQuantidadeEstoque());
            return ResponseEntity.status(HttpStatus.CREATED).body(peca);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Peca> listar() {
        return pecasService.listarTodas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            pecasService.deletarPeca(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public Optional<Peca> buscarPorID(@PathVariable Long id) {
        return pecasService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody PecaDTO pecaDTO) {

        try {
            Peca pecaAtualizar = pecasService.atualizarPeca(id, pecaDTO.getCodigo(), pecaDTO.getDescricao(),
                    pecaDTO.getPrecoUnitario(), pecaDTO.getQuantidadeEstoque());
            return ResponseEntity.ok(pecaAtualizar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
