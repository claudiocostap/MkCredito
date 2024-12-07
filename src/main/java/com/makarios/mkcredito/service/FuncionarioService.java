package com.makarios.mkcredito.service;

import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // 1️⃣ Criar Funcionario
    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // 2️⃣ Listar todos os Funcionarios
    @Transactional(readOnly = true)
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    // 3️⃣ Buscar por ID
    @Transactional(readOnly = true)
    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));
    }

    // 4️⃣ Atualizar Funcionario
    @Transactional
    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = buscarPorId(id);
        funcionarioExistente.setPessoa(funcionarioAtualizado.getPessoa());
        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
        funcionarioExistente.setSalario(funcionarioAtualizado.getSalario());
        funcionarioExistente.setDataContratacao(funcionarioAtualizado.getDataContratacao());
        return funcionarioRepository.save(funcionarioExistente);
    }

    // 5️⃣ Deletar Funcionario
    @Transactional
    public void deletar(Long id) {
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }
}
