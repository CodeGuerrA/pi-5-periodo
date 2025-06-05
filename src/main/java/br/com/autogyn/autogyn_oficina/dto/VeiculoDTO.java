package br.com.autogyn.autogyn_oficina.dto;

import br.com.autogyn.autogyn_oficina.factory.VeiculoFactory;

public class VeiculoDTO {

    private VeiculoFactory.TipoVeiculos tipo;
    private String placa;
    private String modelo;
    private String ano;
    private String cor;

    public VeiculoFactory.TipoVeiculos getTipo() {
        return tipo;
    }

    public void setTipo(VeiculoFactory.TipoVeiculos tipo) {
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
}
