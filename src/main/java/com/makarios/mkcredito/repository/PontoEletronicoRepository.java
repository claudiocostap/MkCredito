package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.PontoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Long> {

    List<PontoEletronico> findByFuncionarioId(Long funcionarioId);

    List<PontoEletronico> findByDataHoraEntradaBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<PontoEletronico> findByFuncionarioIdAndDataHoraEntradaBetween(Long funcionarioId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p FROM PontoEletronico p WHERE p.funcionario.id = :funcionarioId AND p.dataHoraSaida IS NULL")
    Optional<PontoEletronico> findPontoAbertoByFuncionarioId(Long funcionarioId);
}
