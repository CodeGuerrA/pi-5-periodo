package br.com.autogyn.autogyn_oficina.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_peca_os")
public class ItemPecaOS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull(message = "Preço final é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Preço final não pode ser negativo")
    @Column(nullable = false)
    private BigDecimal precoFinal;

    @NotNull(message = "Peça é obrigatória")
    @ManyToOne
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;

    @NotNull(message = "Ordem de serviço é obrigatória")
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    public ItemPecaOS() {
    }

    public ItemPecaOS(Integer quantidade, BigDecimal precoFinal, Peca peca) {
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.peca = peca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(BigDecimal precoFinal) {
        this.precoFinal = precoFinal;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

}
