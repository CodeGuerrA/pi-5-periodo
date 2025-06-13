package br.com.autogyn.autogyn_oficina.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "atribuicao_servico")
public class AtribuicaoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Duração é obrigatória")
    @Column(nullable = false)
    private LocalDateTime duracaoHora;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    @JsonBackReference // Isso informa ao Jackson para não serializar novamente o lado “pai” (evitando
                       // o loop).
    private Funcionario funcionario;

    @OneToOne
    @JoinColumn(name = "servico_id", unique = true)
    @JsonManagedReference // Isso informa ao Jackson para não serializar novamente o lado “pai” (evitando
                          // o loop).
    private Servico servico;

    public AtribuicaoServico() {
    }

    public AtribuicaoServico(String descricao, LocalDateTime duracaoHora) {
        this.descricao = descricao;
        this.duracaoHora = duracaoHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDuracaoHora() {
        return duracaoHora;
    }

    public void setDuracaoHora(LocalDateTime duracaoHora) {
        this.duracaoHora = duracaoHora;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
