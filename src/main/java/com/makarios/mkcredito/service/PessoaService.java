package com.makarios.mkcredito.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.makarios.mkcredito.exceptionhandler.BusinessException;
import com.makarios.mkcredito.exceptionhandler.PessoaNotFoundException;
import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Page<Pessoa> buscarTodas(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        validarPessoaAoSalvar(pessoa);
        pessoa.setId(null); // Garante que estamos criando uma nova pessoa
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException(id));

        validarPessoaAoAtualizar(id, pessoaAtualizada);

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setDocumento(pessoaAtualizada.getDocumento());
        pessoa.setRg(pessoaAtualizada.getRg());
        pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());
        pessoa.setEndereco(pessoaAtualizada.getEndereco());
        pessoa.setAtivo(pessoaAtualizada.getAtivo());

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void remover(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new PessoaNotFoundException(id);
        }
        pessoaRepository.deleteById(id);
    }

    public Optional<Pessoa> buscarPorDocumento(String documento) {
        return pessoaRepository.findByDocumento(documento);
    }

    public Page<Pessoa> buscarPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    private void validarPessoaAoSalvar(Pessoa pessoa) {
        if (pessoa.getDocumento() == null || pessoa.getDocumento().isEmpty()) {
            throw new BusinessException("Documento é obrigatório.");
        }

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new BusinessException("Nome é obrigatório.");
        }

        if (pessoaRepository.findByDocumento(pessoa.getDocumento()).isPresent()) {
            throw new BusinessException("Documento já cadastrado no sistema.");
        }
    }

    private void validarPessoaAoAtualizar(Long id, Pessoa pessoaAtualizada) {
        if (pessoaAtualizada.getDocumento() == null || pessoaAtualizada.getDocumento().isEmpty()) {
            throw new BusinessException("Documento é obrigatório.");
        }

        if (pessoaAtualizada.getNome() == null || pessoaAtualizada.getNome().isEmpty()) {
            throw new BusinessException("Nome é obrigatório.");
        }

        Optional<Pessoa> pessoaComMesmoDocumento = pessoaRepository.findByDocumento(pessoaAtualizada.getDocumento());
        if (pessoaComMesmoDocumento.isPresent() && !pessoaComMesmoDocumento.get().getId().equals(id)) {
            throw new BusinessException("Documento já está sendo utilizado por outra pessoa.");
        }
    }
}
