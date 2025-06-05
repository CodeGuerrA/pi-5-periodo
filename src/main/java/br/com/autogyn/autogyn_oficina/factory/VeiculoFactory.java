package br.com.autogyn.autogyn_oficina.factory;

import br.com.autogyn.autogyn_oficina.model.Carro;
import br.com.autogyn.autogyn_oficina.model.Moto;
import br.com.autogyn.autogyn_oficina.model.Veiculo;

public class VeiculoFactory {
    public enum TipoVeiculos {
        CARRO, MOTO
    }

    public static Veiculo criarVeiculo(TipoVeiculos tipo, String placa, String modelo, String ano, String cor) {
        switch (tipo) {
            case CARRO:
                return new Carro(placa, modelo, ano, cor);
            case MOTO:
                return new Moto(placa, modelo, ano, cor);
            default:
                throw new IllegalArgumentException("Tipo de veículo inválido: " + tipo);
        }
    }
}
