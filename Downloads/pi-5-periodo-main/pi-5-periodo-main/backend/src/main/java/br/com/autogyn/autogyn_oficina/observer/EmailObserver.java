// Padrão de Projeto: Observer

package br.com.autogyn.autogyn_oficina.observer;

public class EmailObserver implements OrdemServicoObserver {

    @Override
    public void notificar(String novaMensagem) {
        System.out.println("[Email] Enviando e-mail para o cliente: " + novaMensagem);
    }
}
