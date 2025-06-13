package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.autogyn.autogyn_oficina.service.AtribuicaoServicoService;
import br.com.autogyn.autogyn_oficina.dto.AtribuicaoServicoDTO;
import br.com.autogyn.autogyn_oficina.entity.AtribuicaoServico;

@RestController
@RequestMapping("/api/atribuicoes-servico")
public class AtribuicaoServicoController {

    @Autowired
    private AtribuicaoServicoService atribuicaoServicoService;

    @GetMapping
    public List<AtribuicaoServico> listarTodos() {
        return atribuicaoServicoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtribuicaoServico> buscarPorId(@PathVariable Long id) {
        AtribuicaoServico atribuicao = atribuicaoServicoService.buscarPorId(id);
        return ResponseEntity.ok(atribuicao);
    }

    @PostMapping
    public ResponseEntity<Void> atribuirServico(@RequestBody AtribuicaoServicoDTO dto) {
        atribuicaoServicoService.atribuirServico(
                dto.getServicoId(),
                dto.getFuncionarioId(),
                dto.getDescricao());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarServico(
            @PathVariable("id") Long atribuicaoId,
            @RequestBody AtribuicaoServicoDTO dto) {

        atribuicaoServicoService.atualizar(
                atribuicaoId,
                dto.getServicoId(),
                dto.getFuncionarioId(),
                dto.getDescricao());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atribuicaoServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
