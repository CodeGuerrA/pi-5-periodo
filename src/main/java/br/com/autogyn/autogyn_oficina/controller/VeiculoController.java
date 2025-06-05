package br.com.autogyn.autogyn_oficina.controller;

import br.com.autogyn.autogyn_oficina.dto.VeiculoDTO;
import br.com.autogyn.autogyn_oficina.model.Veiculo;
import br.com.autogyn.autogyn_oficina.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    // Criar Veiculo, passando try catch para caso houve algum erro {Funcionando}
    @PostMapping
    public ResponseEntity<Veiculo> criarVeiculo(@RequestBody VeiculoDTO dto) {
        try {
            Veiculo veiculo = veiculoService.criarVeiculo(
                    dto.getTipo(),
                    dto.getPlaca(),
                    dto.getModelo(),
                    dto.getAno(),
                    dto.getCor());
            return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Listar Veiculos {Funcionando}
    @GetMapping
    public List<Veiculo> listar() {
        return veiculoService.listarTodos();
    }

    // Buscar por ID funcionando sempre passar o @PathVariable quando for passar id
    // {Funcionando}
    @GetMapping("/{id}")
    public Optional<Veiculo> buscarPorID(@PathVariable Long id) {
        return veiculoService.buscarPorID(id);
    }

    // Atualizar {Funcionando}
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(@RequestBody VeiculoDTO veiculoDTO, @PathVariable Long id) {
        try {
            Veiculo veiculoAtualizado = veiculoService.atualizarVeiculo(id,
                    veiculoDTO.getPlaca(), veiculoDTO.getModelo(), veiculoDTO.getAno(), veiculoDTO.getCor());
            return ResponseEntity.ok(veiculoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // deletar Veiculo {Funcionando}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            veiculoService.deletarVeiculo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
