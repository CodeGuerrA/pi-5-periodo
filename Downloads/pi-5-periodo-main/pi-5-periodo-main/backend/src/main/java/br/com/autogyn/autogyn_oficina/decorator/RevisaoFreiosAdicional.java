// Padrão de Projeto: Decorator

package br.com.autogyn.autogyn_oficina.decorator;

public class RevisaoFreiosAdicional extends ServicoDecorator {

    public RevisaoFreiosAdicional(ServicoAdicional servicoDecorado) {
        super(servicoDecorado);
    }

    @Override
    public String getDescricao() {
        return servicoDecorado.getDescricao() + ", Revisão de Freios Adicional";
    }

    @Override
    public double getCusto() {
        return servicoDecorado.getCusto() + 90.0;
    }
}
