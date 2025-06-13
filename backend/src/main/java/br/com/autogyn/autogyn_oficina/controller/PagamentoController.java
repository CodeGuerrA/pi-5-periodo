package br.com.autogyn.autogyn_oficina.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autogyn.autogyn_oficina.dto.PagamentoDTO;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.entity.Pagamento;
import br.com.autogyn.autogyn_oficina.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<Pagamento> processarPagamento(@RequestBody PagamentoDTO dto) {
        try {
            Pagamento pagamento = new Pagamento();
            pagamento.setFormaPagamento(dto.getFormaPagamento());

            Pagamento processado = pagamentoService.processarPagamento(pagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(processado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Pagamento> listarTodos() {
        return pagamentoService.listarTodos();
    }
}
