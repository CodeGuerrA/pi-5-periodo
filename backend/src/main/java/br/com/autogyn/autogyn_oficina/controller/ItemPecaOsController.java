package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

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

import br.com.autogyn.autogyn_oficina.dto.ItemPecaOsDTO;
import br.com.autogyn.autogyn_oficina.entity.ItemPecaOS;
import br.com.autogyn.autogyn_oficina.service.ItemPecaOsService;

@RestController
@RequestMapping("/api/itens-peca")
public class ItemPecaOsController {

    private final ItemPecaOsService itemPecaOsService;

    public ItemPecaOsController(ItemPecaOsService itemPecaOsService) {
        this.itemPecaOsService = itemPecaOsService;
    }

    @GetMapping
    public List<ItemPecaOS> listarTodos() {
        return itemPecaOsService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPecaOS> buscarPorId(@PathVariable Long id) {
        Optional<ItemPecaOS> itemOptional = itemPecaOsService.buscarPorId(id);
        return itemOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemPecaOS> criarItem(@RequestBody ItemPecaOsDTO dto) {
        try {
            ItemPecaOS novoItem = itemPecaOsService.criarItem(
                    dto.getPecaId(),
                    dto.getQuantidade());
            return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPecaOS> atualizarItem(@PathVariable Long id,
            @RequestBody ItemPecaOsDTO dto) {
        try {
            ItemPecaOS itemAtualizado = itemPecaOsService.atualizarItem(id, dto.getQuantidade());
            return ResponseEntity.ok(itemAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemPecaOsService.deletarItem(id);
        return ResponseEntity.noContent().build();
    }
}
