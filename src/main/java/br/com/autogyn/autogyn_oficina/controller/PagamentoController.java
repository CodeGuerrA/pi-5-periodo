package br.com.autogyn.autogyn_oficina.controller;

import br.com.autogyn.autogyn_oficina.dto.PagamentoDTO;
import br.com.autogyn.autogyn_oficina.model.Pagamento;
import br.com.autogyn.autogyn_oficina.service.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            pagamento.setValorTotal(dto.getValorTotal());
            pagamento.setFormaPagamento(dto.getFormaPagamento());

            Pagamento processado = pagamentoService.processarPagamento(pagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(processado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
