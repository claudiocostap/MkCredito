package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.PontoEletronico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PontoEletronicoRepository extends JpaRepository<PontoEletronico, Long> {

    // Busca todos os registros de ponto de um funcionário específico
    List<PontoEletronico> findByFuncionarioId(Long funcionarioId);

    // Busca registros de ponto por um intervalo de tempo (entrada entre duas datas)
    List<PontoEletronico> findByDataHoraEntradaBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Busca registros de ponto de um funcionário por um intervalo de tempo
    List<PontoEletronico> findByFuncionarioIdAndDataHoraEntradaBetween(Long funcionarioId, LocalDateTime startDate, LocalDateTime endDate);
}
