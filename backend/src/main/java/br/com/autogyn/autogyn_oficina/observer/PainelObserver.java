// Padrão de Projeto: Observer

package br.com.autogyn.autogyn_oficina.observer;

public class PainelObserver implements OrdemServicoObserver {

    @Override
    public void notificar(String novaMensagem) {
        System.out.println("[Painel] Atualização de Ordem de Serviço: " + novaMensagem);
    }
}
