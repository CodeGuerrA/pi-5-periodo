package br.com.autogyn.autogyn_oficina.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.autogyn.autogyn_oficina.enums.TipoVeiculos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculo")
@Inheritance(strategy = InheritanceType.JOINED)
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "A placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}\\d{4}$", message = "Placa inválida. Formato esperado: ABC1234")
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "O modelo é obrigatório")
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Size(min = 4, max = 4, message = "O ano deve ter 4 caracteres")
    @Pattern(regexp = "\\d{4}", message = "Ano deve conter apenas números")
    @Column(name = "ano")
    private String ano;

    @Column(name = "cor")
    private String cor;

    @OneToOne(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrdemServico ordemServico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoVeiculos tipo;

    public Veiculo() {
    }

    public Veiculo(String placa, String modelo, String ano, String cor) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoVeiculos getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculos tipo) {
        this.tipo = tipo;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

}
