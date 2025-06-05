package br.com.autogyn.autogyn_oficina.template;

import br.com.autogyn.autogyn_oficina.model.Pagamento;

public abstract class ProcessadorPagamento {
    public final void processarPagamento(Pagamento pagamento) {
        validar(pagamento);
        executar(pagamento);
        registrar(pagamento);
    }

    protected void validar(Pagamento pagamento) {
        if (pagamento.getValorTotal() == null || pagamento.getValorTotal().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do pagamento inválido");
        }
    }

    protected abstract void executar(Pagamento pagamento);

    protected void registrar(Pagamento pagamento) {
        System.out.println("Pagamento registrado: " + pagamento.getValorTotal());
    }
}

