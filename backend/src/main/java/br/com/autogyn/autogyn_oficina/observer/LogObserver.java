// Padrão de Projeto: Observer

package br.com.autogyn.autogyn_oficina.observer;

public class LogObserver implements OrdemServicoObserver {

    @Override
    public void notificar(String novaMensagem) {
        System.out.println("[Log] Registrando no histórico: " + novaMensagem);
    }
}

