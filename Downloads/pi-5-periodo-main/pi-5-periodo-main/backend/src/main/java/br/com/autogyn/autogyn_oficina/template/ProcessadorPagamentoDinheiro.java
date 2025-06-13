package br.com.autogyn.autogyn_oficina.template;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;

public class ProcessadorPagamentoDinheiro extends ProcessadorPagamento {
    @Override
    protected void executar(Pagamento pagamento) {
        System.out.println("Pagamento realizado em DINHEIRO no valor de: " + pagamento.getValorTotal());
    }
}