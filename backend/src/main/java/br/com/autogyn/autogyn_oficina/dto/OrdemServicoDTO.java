package br.com.autogyn.autogyn_oficina.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;

public class OrdemServicoDTO {
    private LocalDateTime dataAbertura;
    private String descricaoProblema;
    private StatusOrdem status;
    private Long clienteId;
    private Long veiculoId;
    private Long funcionarioId;
    private List<Long> itemPecaIds;
    private List<Integer> quantidadePecas;

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public List<Integer> getQuantidadePecas() {
        return quantidadePecas;
    }

    public void setQuantidadePecas(List<Integer> quantidadePecas) {
        this.quantidadePecas = quantidadePecas;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public List<Long> getItemPecaIds() {
        return itemPecaIds;
    }

    public void setItemPecaIds(List<Long> itemPecaIds) {
        this.itemPecaIds = itemPecaIds;
    }
}
