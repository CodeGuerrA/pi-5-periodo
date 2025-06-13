package br.com.autogyn.autogyn_oficina.dto;

public class ItemPecaOsDTO {

    private Integer quantidade;
    private Long pecaId;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Long getPecaId() {
        return pecaId;
    }

    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }
}