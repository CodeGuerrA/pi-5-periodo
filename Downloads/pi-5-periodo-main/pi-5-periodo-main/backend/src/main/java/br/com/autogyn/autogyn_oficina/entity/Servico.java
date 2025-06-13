package br.com.autogyn.autogyn_oficina.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NotBlank para nao deixar valor nulo ou vazio
    @Column(nullable = false)
    @NotBlank(message = "O nome do serviço é obrigatório")
    private String nome;
    // NotNull orbigatorio colocar o valor
    @Column(nullable = false)
    @NotNull(message = "O valor do serviço é obrigatório")
    @Positive(message = "O valor do serviço deve ser positivo")
    private BigDecimal valor;

    @OneToOne(mappedBy = "servico")
    @JsonBackReference
    private AtribuicaoServico atribuicaoServico;

    public Servico() {
    }

    public Servico(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public AtribuicaoServico getAtribuicaoServico() {
        return atribuicaoServico;
    }

    public void setAtribuicaoServico(AtribuicaoServico atribuicaoServico) {
        this.atribuicaoServico = atribuicaoServico;
    }
}
