package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.autogyn.autogyn_oficina.dto.ClienteDTO;
import br.com.autogyn.autogyn_oficina.model.Cliente;
import br.com.autogyn.autogyn_oficina.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Endpoint para listar todos (PF + PJ) {Funcionando}
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    // Buscar pelo ID o user {Funcionando}
    @GetMapping("/{id}")
    public Optional<Cliente> buscarPorID(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    // Deletenado usuario pelo id dele {Funcionando}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    // Para fazer a atualização vc deve passar o id dps da rota {Funcionando}
    // http://localhost:8080/clientes/1
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente clienteAtualizado = clienteService.atualizarCliente(
                    id,
                    clienteDTO.getNome(),
                    clienteDTO.getEndereco(),
                    clienteDTO.getTelefone(),
                    clienteDTO.getDocumento());
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            // Pode ser documento inválido ou tipo de cliente incompatível
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // Caso o cliente não exista, pode retornar 404
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint
    // Va na classe DTO ira entender sua funcionalidade
    // Para esconder tbm valores que nao quero que apareça ele esta sendo usado para
    // nao precisar passar varios parametros em RequestBody e somente os que quero.

    // Criar Cliente {Funcionando}
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.criarCliente(
                    clienteDTO.getNome(),
                    clienteDTO.getEndereco(),
                    clienteDTO.getTelefone(),
                    clienteDTO.getDocumento());
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // ou incluir mensagem: .body(null)
        }
    }
}
