package br.com.autogyn.autogyn_oficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.autogyn.autogyn_oficina.entity.ClientePJ;

@Repository
public interface ClientePJRepository extends JpaRepository<ClientePJ, Long> {
}
