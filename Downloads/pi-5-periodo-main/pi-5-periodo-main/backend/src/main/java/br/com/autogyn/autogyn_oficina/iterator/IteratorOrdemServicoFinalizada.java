package br.com.autogyn.autogyn_oficina.iterator;

import java.util.List;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;

public class IteratorOrdemServicoFinalizada implements OrdemServicoIterator {

    private final List<OrdemServico> ordens;
    private int posicaoAtual = 0;

    public IteratorOrdemServicoFinalizada(List<OrdemServico> ordens) {
        this.ordens = ordens;
    }

    @Override
    public boolean hasNext() {
        while (posicaoAtual < ordens.size()) {
            if (ordens.get(posicaoAtual).getStatus() == StatusOrdem.FINALIZADA) {
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
