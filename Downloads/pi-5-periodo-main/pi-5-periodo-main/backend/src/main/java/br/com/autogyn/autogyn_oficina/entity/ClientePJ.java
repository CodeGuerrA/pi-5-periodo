package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

//Entidade
@Entity
@Table(name = "cliente_pj")
public class ClientePJ extends Cliente {

    @Column(name = "cnpj", nullable = false, unique = true)
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ inválido. O formato correto é 00.000.000/0000-00")
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