// Padrão de Projeto: Iterator

package br.com.autogyn.autogyn_oficina.iterator;

import java.util.List;

import br.com.autogyn.autogyn_oficina.entity.OrdemServico;

public class ColecaoOrdensServico {

    private final List<OrdemServico> ordens;

    public ColecaoOrdensServico(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

    public OrdemServicoIterator iteratorAberta() {
        return new IteratorOrdemServicoAberta(ordens);
    }

    public OrdemServicoIterator iteratorFinalizada() {
        return new IteratorOrdemServicoFinalizada(ordens);
    }

    public OrdemServicoIterator iteratorCancelada() {
        return new IteratorOrdemServicoCancelada(ordens);
    }

    public OrdemServicoIterator iteratorTodos() {
        return new IteratorOrdemServicoTodos(ordens);
    }
}
