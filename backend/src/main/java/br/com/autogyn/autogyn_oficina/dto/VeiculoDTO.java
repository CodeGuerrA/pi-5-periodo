package br.com.autogyn.autogyn_oficina.dto;

import br.com.autogyn.autogyn_oficina.enums.TipoVeiculos;

public class VeiculoDTO {

    private TipoVeiculos tipo;
    private String placa;
    private String modelo;
    private String ano;
    private String cor;
    private Long clienteId;

    public TipoVeiculos getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculos tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

}
