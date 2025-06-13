package br.com.autogyn.autogyn_oficina.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "moto")
public class Moto extends Veiculo {

    public Moto() {
    }

    public Moto(String placa, String modelo, String ano, String cor) {
        super(placa, modelo, ano, cor);
    }
}
