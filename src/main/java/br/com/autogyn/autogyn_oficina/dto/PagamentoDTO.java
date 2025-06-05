package br.com.autogyn.autogyn_oficina.dto;

import java.math.BigDecimal;

public class PagamentoDTO {
    private BigDecimal valorTotal;
    private String formaPagamento;

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
