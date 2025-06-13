package br.com.autogyn.autogyn_oficina.iterator;

import java.util.List;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;

public class IteratorOrdemServicoTodos implements OrdemServicoIterator {

    private final List<OrdemServico> ordens;
    private int posicaoAtual = 0;

    public IteratorOrdemServicoTodos(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

    @Override
    public boolean hasNext() {
        return posicaoAtual < ordens.size();
    }

    @Override
    public OrdemServico next() {
        return ordens.get(posicaoAtual++);
    }
}
