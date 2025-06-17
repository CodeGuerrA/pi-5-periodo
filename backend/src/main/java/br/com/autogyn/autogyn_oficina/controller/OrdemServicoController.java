package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import br.com.autogyn.autogyn_oficina.dto.FinalizacaoOrdemDTO;
import br.com.autogyn.autogyn_oficina.dto.OrdemServicoDTO;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.service.OrdemServicoService;
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @GetMapping
    public List<OrdemServico> listarTodos() {
        return ordemServicoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<OrdemServico> buscarPorId(@PathVariable Long id) {
        return ordemServicoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> criarOrdemServico(@RequestBody OrdemServicoDTO dto) {
        try {
            if (dto.getItemPecaIds() == null || dto.getItemPecaIds().isEmpty()) {
                return ResponseEntity.badRequest().body("É necessário informar pelo menos um item de peça.");
            }
            if (dto.getServicoIds() == null || dto.getServicoIds().isEmpty()) {
                return ResponseEntity.badRequest().body("É necessário informar pelo menos um serviço.");
            }

            OrdemServico ordem = new OrdemServico();
            ordem.setDescricaoProblema(dto.getDescricaoProblema());
            ordem.setStatus(dto.getStatus());

            OrdemServico novaOrdem = ordemServicoService.criar(
                    ordem,
                    dto.getClienteId(),
                    dto.getVeiculoId(),
                    dto.getItemPecaIds(),
                    dto.getQuantidadePecas(),
                    dto.getServicoIds());

            return ResponseEntity.ok(novaOrdem);
        } catch (EntityNotFoundException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarOrdemServico(
            @PathVariable Long id,
            @RequestBody OrdemServicoDTO dto) {
        try {
            OrdemServico ordem = new OrdemServico();
            ordem.setDescricaoProblema(dto.getDescricaoProblema());
            ordem.setStatus(dto.getStatus());

            OrdemServico atualizada = ordemServicoService.atualizaOrdemServico(
                    id,
                    ordem,
                    dto.getClienteId(),
                    dto.getVeiculoId(),
                    dto.getItemPecaIds(),
                    dto.getQuantidadePecas(),
                    dto.getServicoIds());

            return ResponseEntity.ok(atualizada);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            ordemServicoService.deletarOrdemServico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizarOrdemServico(@RequestBody FinalizacaoOrdemDTO dto) {
        try {
            ordemServicoService.finalizarOrdemServico(dto.getIdOrdemServico(), dto.getFormaPagamento());
            return ResponseEntity.ok("Finalizada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/relatorio-financeiro")
    public ResponseEntity<byte[]> gerarRelatorioFinanceiro(@PathVariable Long id) {
        byte[] pdf = ordemServicoService.gerarRelatorioFinanceiro(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_os_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
