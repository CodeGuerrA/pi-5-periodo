package br.com.autogyn.autogyn_oficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autogyn.autogyn_oficina.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
