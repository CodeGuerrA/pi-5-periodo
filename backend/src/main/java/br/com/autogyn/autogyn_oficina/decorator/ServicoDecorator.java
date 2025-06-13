package br.com.autogyn.autogyn_oficina.decorator;

public abstract class ServicoDecorator implements ServicoAdicional {

    protected ServicoAdicional servicoDecorado;

    public ServicoDecorator(ServicoAdicional servicoDecorado) {
        this.servicoDecorado = servicoDecorado;
    }
}
