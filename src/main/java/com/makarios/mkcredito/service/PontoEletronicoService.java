package com.makarios.mkcredito.service;

import com.makarios.mkcredito.exceptionhandler.RecursoNaoEncontradoException;
import com.makarios.mkcredito.exceptionhandler.RegistroDePontoException;
import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.repository.FuncionarioRepository;
import com.makarios.mkcredito.repository.PontoEletronicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PontoEletronicoService {

    private final PontoEletronicoRepository pontoEletronicoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public PontoEletronicoService(PontoEletronicoRepository pontoEletronicoRepository, FuncionarioRepository funcionarioRepository) {
        this.pontoEletronicoRepository = pontoEletronicoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public PontoEletronico registrarEntrada(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com o ID: " + funcionarioId));

        pontoEletronicoRepository.findPontoAbertoByFuncionarioId(funcionarioId).ifPresent(p -> {
            throw new RegistroDePontoException("Funcionário já possui um ponto de entrada em aberto.");
        });

        PontoEletronico novoPonto = new PontoEletronico();
        novoPonto.setFuncionario(funcionario);
        return pontoEletronicoRepository.save(novoPonto);
    }

    @Transactional
    public PontoEletronico registrarSaida(Long funcionarioId) {
        PontoEletronico pontoAberto = pontoEletronicoRepository.findPontoAbertoByFuncionarioId(funcionarioId)
                .orElseThrow(() -> new RegistroDePontoException("Nenhum ponto de entrada em aberto encontrado para o funcionário."));

        pontoAberto.setDataHoraSaida(LocalDateTime.now());
        return pontoEletronicoRepository.save(pontoAberto);
    }

    @Transactional
    public PontoEletronico adicionarJustificativa(Long pontoId, String justificativa) {
        PontoEletronico ponto = buscarPorId(pontoId);
        ponto.setJustificativa(justificativa);
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
}
