package com.makarios.mkcredito.service;

import com.makarios.mkcredito.exceptionhandler.BusinessException;
import com.makarios.mkcredito.exceptionhandler.RecursoNaoEncontradoException;
import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Page<Pessoa> buscarTodas(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada com o ID: " + id));
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        validarDocumentoDuplicado(pessoa);
        pessoa.setId(null); 
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoaSalva = buscarPorId(id);
        validarDocumentoDuplicado(pessoaAtualizada, id);

        BeanUtils.copyProperties(pessoaAtualizada, pessoaSalva, "id");
        return pessoaRepository.save(pessoaSalva);
    }

    @Transactional
    public void remover(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    public Optional<Pessoa> buscarPorDocumento(String documento) {
        return pessoaRepository.findByDocumento(documento);
    }

    public Page<Pessoa> buscarPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    private void validarDocumentoDuplicado(Pessoa pessoa) {
        validarDocumentoDuplicado(pessoa, null);
    }

    private void validarDocumentoDuplicado(Pessoa pessoa, Long idExcluido) {
        Optional<Pessoa> pessoaComMesmoDocumento = pessoaRepository.findByDocumento(pessoa.getDocumento());
        if (pessoaComMesmoDocumento.isPresent() && (idExcluido == null || !pessoaComMesmoDocumento.get().getId().equals(idExcluido))) {
            throw new BusinessException("Documento já está sendo utilizado por outra pessoa.");
        }
    }
}
