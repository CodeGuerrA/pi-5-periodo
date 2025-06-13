package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

//Entidade
@Entity
@Table(name = "cliente_pf")
public class ClientePF extends Cliente {

    // Usei a bean validation para fazer um regex para validar as entradas de cpf e
    // cnpj
    @Column(name = "cpf", nullable = false, unique = true)
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido. Use o formato 000.000.000-00")
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
