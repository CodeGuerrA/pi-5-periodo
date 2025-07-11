package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.autogyn.autogyn_oficina.enums.StatusPagamento;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Valor total deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal valorTotal;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    @Column(nullable = false)
    private String formaPagamento;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    public Pagamento() {
    }

    
    public Pagamento(BigDecimal valorTotal, String formaPagamento, LocalDate dataPagamento, StatusPagamento status) {
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }


    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

}
