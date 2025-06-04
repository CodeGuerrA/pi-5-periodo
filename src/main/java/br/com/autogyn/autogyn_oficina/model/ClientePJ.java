package br.com.autogyn.autogyn_oficina.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//Entidade
@Entity
@Table(name = "cliente_pj")
public class ClientePJ extends Cliente {

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    protected ClientePJ() {
    }

    public ClientePJ(String nome, String endereco, String telefone, String cnpj) {
        super(nome, endereco, telefone);
        this.cnpj = cnpj;
    }

    // getter e setter do cnpj
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}