// Padrão de Projeto: Decorator

package br.com.autogyn.autogyn_oficina.decorator;

public class GarantiaEstendida extends ServicoDecorator {

    public GarantiaEstendida(ServicoAdicional servicoDecorado) {
        super(servicoDecorado);
    }

    @Override
    public String getDescricao() {
        return servicoDecorado.getDescricao() + ", Garantia Estendida";
    }

    @Override
    public double getCusto() {
        return servicoDecorado.getCusto() + 120.0;
    }
}
