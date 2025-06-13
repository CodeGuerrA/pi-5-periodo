package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "peca")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Código é obrigatório")
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Preço unitário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço unitário deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade em estoque não pode ser negativa")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    public Peca() {
    }

    public Peca(String codigo, String descricao, BigDecimal precoUnitario, Integer quantidadeEstoque) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
