package com.makarios.mkcredito.service;

import com.makarios.mkcredito.dto.ResumoPontoDTO;
import com.makarios.mkcredito.exceptionhandler.RecursoNaoEncontradoException;
import com.makarios.mkcredito.exceptionhandler.RegistroDePontoException;
import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.repository.FuncionarioRepository;
import com.makarios.mkcredito.repository.PontoEletronicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PontoEletronicoService {

    private final PontoEletronicoRepository pontoEletronicoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private static final long MINUTOS_JORNADA_DIARIA = 480; // 8 horas

    public PontoEletronicoService(PontoEletronicoRepository pontoEletronicoRepository, FuncionarioRepository funcionarioRepository) {
        this.pontoEletronicoRepository = pontoEletronicoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public PontoEletronico registrarEntrada(Long funcionarioId, Long usuarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + funcionarioId));

        pontoEletronicoRepository.findPontoAbertoByFuncionarioId(funcionarioId).ifPresent(p -> {
            throw new RegistroDePontoException("Funcionário já possui um ponto de entrada em aberto.");
        });

        PontoEletronico novoPonto = new PontoEletronico();
        novoPonto.setFuncionario(funcionario);
        novoPonto.setCriadoPorId(usuarioId);
        novoPonto.setAlteradoPorId(usuarioId);
        return pontoEletronicoRepository.save(novoPonto);
    }

    @Transactional
    public PontoEletronico registrarSaida(Long funcionarioId, Long usuarioId) {
        PontoEletronico pontoAberto = pontoEletronicoRepository.findPontoAbertoByFuncionarioId(funcionarioId)
                .orElseThrow(() -> new RegistroDePontoException("Nenhum ponto de entrada em aberto encontrado para o funcionário."));

        pontoAberto.setDataHoraSaida(LocalDateTime.now());
        pontoAberto.setAlteradoPorId(usuarioId);
        return pontoEletronicoRepository.save(pontoAberto);
    }

    @Transactional
    public PontoEletronico adicionarJustificativa(Long pontoId, String justificativa, Long usuarioId) {
        PontoEletronico ponto = buscarPorId(pontoId);
        ponto.setJustificativa(justificativa);
        ponto.setAlteradoPorId(usuarioId);
        return pontoEletronicoRepository.save(ponto);
    }

    public PontoEletronico buscarPorId(Long id) {
        return pontoEletronicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ponto eletrônico não encontrado para o ID: " + id));
    }

    public List<PontoEletronico> listarTodos() {
        return pontoEletronicoRepository.findAll();
    }

    public List<PontoEletronico> listarPorFuncionario(Long funcionarioId) {
        return pontoEletronicoRepository.findByFuncionarioId(funcionarioId);
    }

    @Transactional
    public void deletar(Long id) {
        PontoEletronico pontoEletronico = buscarPorId(id);
        pontoEletronicoRepository.delete(pontoEletronico);
    }

    public ResumoPontoDTO calcularResumoHoras(Long funcionarioId, LocalDateTime inicio, LocalDateTime fim) {
        List<PontoEletronico> pontos = pontoEletronicoRepository.findByFuncionarioIdAndDataHoraEntradaBetween(funcionarioId, inicio, fim);

        Map<LocalDate, List<PontoEletronico>> pontosPorDia = pontos.stream()
                .filter(p -> p.getDataHoraSaida() != null) // Considerar apenas pontos fechados
                .collect(Collectors.groupingBy(p -> p.getDataHoraEntrada().toLocalDate()));

        long totalMinutosTrabalhados = 0;
        long totalMinutosExtras = 0;

        for (List<PontoEletronico> pontosDoDia : pontosPorDia.values()) {
            long minutosNoDia = 0;
            for (PontoEletronico ponto : pontosDoDia) {
                Duration duracao = Duration.between(ponto.getDataHoraEntrada(), ponto.getDataHoraSaida());
                minutosNoDia += duracao.toMinutes();
            }

            totalMinutosTrabalhados += minutosNoDia;

            if (minutosNoDia > MINUTOS_JORNADA_DIARIA) {
                totalMinutosExtras += (minutosNoDia - MINUTOS_JORNADA_DIARIA);
            }
        }

        return new ResumoPontoDTO(totalMinutosTrabalhados, totalMinutosExtras);
    }
}
