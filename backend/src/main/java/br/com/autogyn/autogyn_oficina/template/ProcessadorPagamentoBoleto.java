package br.com.autogyn.autogyn_oficina.template;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;

public class ProcessadorPagamentoBoleto extends ProcessadorPagamento {
    @Override
    protected void executar(Pagamento pagamento) {
        System.out.println("Pagamento gerado via BOLETO. Valor: " + pagamento.getValorTotal());
    }
}