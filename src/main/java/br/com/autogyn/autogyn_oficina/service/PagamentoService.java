package br.com.autogyn.autogyn_oficina.service;

import br.com.autogyn.autogyn_oficina.model.Pagamento;
import br.com.autogyn.autogyn_oficina.model.StatusPagamento;
import br.com.autogyn.autogyn_oficina.repository.PagamentoRepository;
import br.com.autogyn.autogyn_oficina.template.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

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
        pagamento.setStatus(StatusPagamento.PAGO); // definimos como pago aqui

        processador.processarPagamento(pagamento); // aplica o template method

        return pagamentoRepository.save(pagamento);
    }
}

