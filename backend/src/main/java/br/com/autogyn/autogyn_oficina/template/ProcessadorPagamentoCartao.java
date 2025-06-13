package br.com.autogyn.autogyn_oficina.template;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;

public class ProcessadorPagamentoCartao extends ProcessadorPagamento {
    @Override
    protected void executar(Pagamento pagamento) {
        System.out.println("Processando pagamento via CARTÃO: " + pagamento.getValorTotal());
    }
}
