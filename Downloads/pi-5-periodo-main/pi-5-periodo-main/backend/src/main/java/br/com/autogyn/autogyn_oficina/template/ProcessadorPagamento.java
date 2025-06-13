package br.com.autogyn.autogyn_oficina.template;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;

//Padrão Template para criar um processador de pagamento com diversas formas de pagamento
public abstract class ProcessadorPagamento {
    public final void processarPagamento(Pagamento pagamento) {
        validar(pagamento);
        executar(pagamento);
        registrar(pagamento);
    }

    // Validação do pagamento
    protected void validar(Pagamento pagamento) {
        if (pagamento.getValorTotal() == null || pagamento.getValorTotal().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do pagamento inválido");
        }
    }

    // Executar ele
    protected abstract void executar(Pagamento pagamento);

    // Registrar Pagamento
    protected void registrar(Pagamento pagamento) {
        System.out.println("Pagamento registrado: " + pagamento.getValorTotal());
    }
}
