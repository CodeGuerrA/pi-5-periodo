package br.com.autogyn.autogyn_oficina.dto;

import java.math.BigDecimal;

//usar o BigDecimal para mexer com valores financeiros
public class ServicoDTO {
    private String nome;
    private BigDecimal valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
