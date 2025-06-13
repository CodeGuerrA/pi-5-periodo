// Padrão de Projeto: Decorator

package br.com.autogyn.autogyn_oficina.decorator;

public class LavagemGratis extends ServicoDecorator {

    public LavagemGratis(ServicoAdicional servicoDecorado) {
        super(servicoDecorado);
    }

    @Override
    public String getDescricao() {
        return servicoDecorado.getDescricao() + ", Lavagem Grátis";
    }

    @Override
    public double getCusto() {
        return servicoDecorado.getCusto(); // grátis!
    }
}

