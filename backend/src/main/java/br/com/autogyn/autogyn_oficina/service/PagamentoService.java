package br.com.autogyn.autogyn_oficina.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;
import br.com.autogyn.autogyn_oficina.enums.StatusPagamento;
import br.com.autogyn.autogyn_oficina.repository.PagamentoRepository;
import br.com.autogyn.autogyn_oficina.template.ProcessadorPagamento;
import br.com.autogyn.autogyn_oficina.template.ProcessadorPagamentoBoleto;
import br.com.autogyn.autogyn_oficina.template.ProcessadorPagamentoCartao;
import br.com.autogyn.autogyn_oficina.template.ProcessadorPagamentoDinheiro;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public Pagamento processarPagamento(Pagamento pagamento) {
        // Escolhe dinamicamente o processador com base na forma de pagamento
        ProcessadorPagamento processador;

        switch (pagamento.getFormaPagamento().toLowerCase()) {
            case "cartao" -> processador = new ProcessadorPagamentoCartao();
            case "dinheiro" -> processador = new ProcessadorPagamentoDinheiro();
            case "boleto" -> processador = new ProcessadorPagamentoBoleto();
            default -> throw new IllegalArgumentException("Forma de pagamento inválida");
        }

        pagamento.setDataPagamento(LocalDate.now());
        pagamento.setStatus(StatusPagamento.PAGO);

        processador.processarPagamento(pagamento);

        return pagamentoRepository.save(pagamento);
    }
}
