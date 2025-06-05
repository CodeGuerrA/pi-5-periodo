package br.com.autogyn.autogyn_oficina.repository;

import br.com.autogyn.autogyn_oficina.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
