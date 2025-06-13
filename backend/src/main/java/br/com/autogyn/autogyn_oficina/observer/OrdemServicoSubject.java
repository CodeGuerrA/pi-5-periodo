// Padrão de Projeto: Observer

package br.com.autogyn.autogyn_oficina.observer;

import java.util.ArrayList;
import java.util.List;

public class OrdemServicoSubject {

    private final List<OrdemServicoObserver> observadores = new ArrayList<>();

    // Adiciona um observador
    public void adicionarObservador(OrdemServicoObserver observador) {
        observadores.add(observador);
    }

    // Remove um observador
    public void removerObservador(OrdemServicoObserver observador) {
        observadores.remove(observador);
    }

    // Notifica todos os observadores registrados
    public void notificarTodos(String mensagem) {
        for (OrdemServicoObserver observador : observadores) {
            observador.notificar(mensagem);
        }
    }
}
