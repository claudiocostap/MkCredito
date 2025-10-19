package com.makarios.mkcredito.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.makarios.mkcredito.model.PontoEletronico;
import com.makarios.mkcredito.repository.PontoEletronicoRepository;

@Service
public class PontoEletronicoService {

    private final PontoEletronicoRepository pontoEletronicoRepository;

    public PontoEletronicoService(PontoEletronicoRepository pontoEletronicoRepository) {
        this.pontoEletronicoRepository = pontoEletronicoRepository;
    }

    // Listar todos os pontos eletrônicos
    public List<PontoEletronico> listarTodos() {
        return pontoEletronicoRepository.findAll();
    }

    // Buscar ponto eletrônico por ID
    public PontoEletronico buscarPorId(Long id) {
        return pontoEletronicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto eletrônico não encontrado para o ID: \" + id"));
    }

    // Salvar um novo ponto eletrônico
    @Transactional
    public PontoEletronico salvar(PontoEletronico pontoEletronico) {
        return pontoEletronicoRepository.save(pontoEletronico);
    }

    // Atualizar um ponto eletrônico existente
    @Transactional
    public PontoEletronico atualizar(Long id, PontoEletronico pontoEletronicoAtualizado) {
        PontoEletronico pontoExistente = buscarPorId(id);
        pontoExistente.setDataHoraSaida(pontoEletronicoAtualizado.getDataHoraSaida());
        pontoExistente.setJustificativa(pontoEletronicoAtualizado.getJustificativa());
        return pontoEletronicoRepository.save(pontoExistente);
    }

    // Deletar ponto eletrônico por ID
    @Transactional
    public void deletar(Long id) {
        PontoEletronico pontoEletronico = buscarPorId(id);
        pontoEletronicoRepository.delete(pontoEletronico);
    }
}
