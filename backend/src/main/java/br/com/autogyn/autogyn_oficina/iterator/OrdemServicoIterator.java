// Padrão de Projeto: Iterator

package br.com.autogyn.autogyn_oficina.iterator;

import br.com.autogyn.autogyn_oficina.entity.OrdemServico;

public interface OrdemServicoIterator {
    boolean hasNext();
    OrdemServico next();
}
