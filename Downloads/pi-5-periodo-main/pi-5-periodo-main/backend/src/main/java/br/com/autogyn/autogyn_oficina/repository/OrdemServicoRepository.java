package br.com.autogyn.autogyn_oficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.enums.StatusOrdem;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    // verifica se um veiculo ja esta em alguma OS
    boolean existsByVeiculoIdAndStatusIn(Long veiculoId, List<StatusOrdem> statusList);

}