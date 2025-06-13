package br.com.autogyn.autogyn_oficina.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "funcionario") // Corrigido o nome da tabela
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    @Column(name = "cargo", nullable = false)
    private String cargo; // corrigido para minúsculo

    // Um funcionário para vários serviços
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AtribuicaoServico> atribuicoes = new ArrayList<>();

    @OneToMany(mappedBy = "funcionario")
    @JsonManagedReference
    private List<OrdemServico> ordensServico;

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<AtribuicaoServico> getAtribuicoes() {
        return atribuicoes;
    }

    public void setAtribuicoes(List<AtribuicaoServico> atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

    public List<OrdemServico> getOrdensServico() {
        return ordensServico;
    }

    public void setOrdensServico(List<OrdemServico> ordensServico) {
        this.ordensServico = ordensServico;
    }
}
