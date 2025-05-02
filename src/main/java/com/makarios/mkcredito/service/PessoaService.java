package com.makarios.mkcredito.service;

import com.makarios.mkcredito.exceptionhandler.BusinessException;
import com.makarios.mkcredito.exceptionhandler.PessoaNotFoundException;
import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.repository.PessoaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException(id));

        validarPessoaAoAtualizar(id, pessoaAtualizada);

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
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

    public Optional<Pessoa> buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }

    public Page<Pessoa> buscarPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    private void validarPessoaAoSalvar(Pessoa pessoa) {
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()) {
            throw new BusinessException("CPF é obrigatório.");
        }

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new BusinessException("Nome é obrigatório.");
        }

        if (pessoaRepository.findByCpf(pessoa.getCpf()).isPresent()) {
            throw new BusinessException("CPF já cadastrado no sistema.");
        }
    }

    private void validarPessoaAoAtualizar(Long id, Pessoa pessoaAtualizada) {
        if (pessoaAtualizada.getCpf() == null || pessoaAtualizada.getCpf().isEmpty()) {
            throw new BusinessException("CPF é obrigatório.");
        }

        if (pessoaAtualizada.getNome() == null || pessoaAtualizada.getNome().isEmpty()) {
            throw new BusinessException("Nome é obrigatório.");
        }

        Optional<Pessoa> pessoaComMesmoCpf = pessoaRepository.findByCpf(pessoaAtualizada.getCpf());
        if (pessoaComMesmoCpf.isPresent() && !pessoaComMesmoCpf.get().getId().equals(id)) {
            throw new BusinessException("CPF já está sendo utilizado por outra pessoa.");
        }
    }
}
