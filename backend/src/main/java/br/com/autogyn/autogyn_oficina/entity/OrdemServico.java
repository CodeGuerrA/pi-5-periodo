package br.com.autogyn.autogyn_oficina.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data de abertura é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    @Column
    private LocalDateTime dataFechamento;

    @Enumerated(EnumType.STRING)
    private StatusOrdem status;

    @NotBlank(message = "Descrição do problema é obrigatória")
    @Size(max = 1000, message = "Descrição do problema pode ter no máximo 1000 caracteres")
    @Column(length = 1000, nullable = false)
    private String descricaoProblema;

    // Relacionamentos:

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Veículo é obrigatório")
    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "veiculo_id", unique = true, nullable = false)
    private Veiculo veiculo;

    @OneToMany(mappedBy = "ordemServico")
    @JsonManagedReference
    private List<ItemPecaOS> itensPeca;

    @NotEmpty(message = "A ordem de serviço deve conter ao menos um serviço")
    @ManyToMany
    @JsonManagedReference
    private List<Servico> servicos = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    public OrdemServico() {
    }

    public OrdemServico(LocalDateTime dataAbertura, LocalDateTime dataFechamento, StatusOrdem status,
            String descricaoProblema) {
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.status = status;
        this.descricaoProblema = descricaoProblema;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<ItemPecaOS> getItensPeca() {
        return itensPeca;
    }

    public void setItensPeca(List<ItemPecaOS> itensPeca) {
        this.itensPeca = itensPeca;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

}
