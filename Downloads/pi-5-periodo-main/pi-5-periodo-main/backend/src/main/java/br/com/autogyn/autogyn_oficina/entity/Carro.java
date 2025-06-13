package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "carro")
public class Carro extends Veiculo {

    public Carro() {
    }

    public Carro(String placa, String modelo, String ano, String cor) {
        super(placa, modelo, ano, cor);
    }
}
