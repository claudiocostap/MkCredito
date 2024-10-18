package com.makarios.mkcredito.service;

import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.model.PontoEletronico;

import com.makarios.mkcredito.repository.FuncionarioRepository;
import com.makarios.mkcredito.repository.PontoEletronicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PontoEletronicoService {

    @Autowired
    private PontoEletronicoRepository pontoEletronicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public Duration calcularHorasTrabalhadas(PontoEletronico ponto) {
        LocalDateTime entrada = ponto.getDataHoraEntrada();
        LocalDateTime saida = ponto.getDataHoraSaida();

        if (entrada != null && saida != null) {
            return Duration.between(entrada, saida);
        }
        return Duration.ZERO;
    }

    public Duration calcularHorasExtras(PontoEletronico ponto) {
        Duration horasTrabalhadas = calcularHorasTrabalhadas(ponto);
        Duration horasRegulamentares = Duration.ofHours(8);

        return horasTrabalhadas.minus(horasRegulamentares).isNegative() ? Duration.ZERO : horasTrabalhadas.minus(horasRegulamentares);
    }

    // Registrar entrada de um funcionário
    public PontoEletronico registrarEntrada(Long funcionarioId) {
        // Buscar o funcionário pelo ID
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        // Criar o registro de ponto
        PontoEletronico ponto = new PontoEletronico();
        ponto.setFuncionario(funcionario);  // Associando o funcionário ao ponto
        ponto.setDataHoraEntrada(LocalDateTime.now());
        return pontoEletronicoRepository.save(ponto);
    }

    // Registrar saída de um funcionário
    public PontoEletronico registrarSaida(Long pontoId) {
        // Buscar o registro de ponto pelo ID
        PontoEletronico ponto = pontoEletronicoRepository.findById(pontoId)
                .orElseThrow(() -> new RuntimeException("Registro de ponto não encontrado"));

        // Registrar a data e hora da saída
        ponto.setDataHoraSaida(LocalDateTime.now());
        return pontoEletronicoRepository.save(ponto);
    }

    // Buscar todos os registros de um funcionário em um intervalo de datas
    public List<PontoEletronico> buscarRegistrosPorFuncionarioEIntervalo(Long funcionarioId, LocalDateTime start, LocalDateTime end) {
        return pontoEletronicoRepository.findByFuncionarioIdAndDataHoraEntradaBetween(funcionarioId, start, end);
    }
}
