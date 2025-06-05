package br.com.autogyn.autogyn_oficina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.autogyn.autogyn_oficina.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

}
