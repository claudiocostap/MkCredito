package com.makarios.mkcredito.service;

import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    // Buscar todas as pessoas no banco de dados
    public List<Pessoa> buscarTodas() {
        return pessoaRepository.findAll();
    }

    // Buscar pessoa por ID
    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    // Salvar uma nova pessoa
    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        validarPessoaAoSalvar(pessoa);
        return pessoaRepository.save(pessoa);
    }

    // Atualizar uma pessoa existente
    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);

        if (!pessoaExistente.isPresent()) {
            throw new IllegalArgumentException("Pessoa com ID " + id + " não encontrada.");
        }

        validarPessoaAoAtualizar(id, pessoaAtualizada);

        // Mantém o ID da pessoa original para garantir que estamos atualizando o registro correto
        Pessoa pessoa = pessoaExistente.get();
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setRg(pessoaAtualizada.getRg());
        pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());
        pessoa.setEndereco(pessoaAtualizada.getEndereco());
        pessoa.setAtivo(pessoaAtualizada.getAtivo());

        return pessoaRepository.save(pessoa);
    }

    // Remover uma pessoa pelo ID
    @Transactional
    public void remover(Long id) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);

        if (pessoaExistente.isEmpty()) {
            throw new IllegalArgumentException("Pessoa com ID " + "id não encontrada.");
        }

        pessoaRepository.deleteById(id);
    }

    // Buscar pessoa por CPF
    public Optional<Pessoa> buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf);
    }

    // Buscar pessoa por nome (ou parte do nome)
    public List<Pessoa> buscarPorNome(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Validação ao salvar uma nova pessoa
    private void validarPessoaAoSalvar(Pessoa pessoa) {
        if (pessoa.getCpf() == null || pessoa.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }

        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        // Verifica se o CPF já está sendo utilizado
        Optional<Pessoa> pessoaExistente = pessoaRepository.findByCpf(pessoa.getCpf());
        if (pessoaExistente.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }
    }

    // Validação ao atualizar uma pessoa existente
    private void validarPessoaAoAtualizar(Long id, Pessoa pessoaAtualizada) {
        if (pessoaAtualizada.getCpf() == null || pessoaAtualizada.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }

        if (pessoaAtualizada.getNome() == null || pessoaAtualizada.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        // Verifica se o CPF está sendo utilizado por outra pessoa (diferente da pessoa sendo atualizada)
        Optional<Pessoa> pessoaComMesmoCpf = pessoaRepository.findByCpf(pessoaAtualizada.getCpf());
        if (pessoaComMesmoCpf.isPresent() && !pessoaComMesmoCpf.get().getId().equals(id)) {
            throw new IllegalArgumentException("CPF já está sendo utilizado por outra pessoa.");
        }
    }
}
