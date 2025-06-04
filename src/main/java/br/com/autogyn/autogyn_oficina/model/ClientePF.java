package br.com.autogyn.autogyn_oficina.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

//Entidade
@Entity
@Table(name = "cliente_pf")
public class ClientePF extends Cliente {

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    public ClientePF() {
    }

    public ClientePF(String nome, String endereco, String telefone, String cpf) {
        super(nome, endereco, telefone);
        this.cpf = cpf;
    }

    // Getters e setters para cpf
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
