package br.com.autogyn.autogyn_oficina.iterator;

import java.util.List;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;

public class IteratorOrdemServicoCancelada implements OrdemServicoIterator {

    private final List<OrdemServico> ordens;
    private int posicaoAtual = 0;

    public IteratorOrdemServicoCancelada(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

    @Override
    public boolean hasNext() {
        while (posicaoAtual < ordens.size()) {
            if (ordens.get(posicaoAtual).getStatus() == StatusOrdem.CANCELADA) {
                return true;
            }
            posicaoAtual++;
        }
        return false;
    }

    @Override
    public OrdemServico next() {
        return ordens.get(posicaoAtual++);
    }
}
