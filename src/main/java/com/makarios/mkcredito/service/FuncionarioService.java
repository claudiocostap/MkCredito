package com.makarios.mkcredito.service;

import com.makarios.mkcredito.model.Funcionario;
import com.makarios.mkcredito.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // 1️⃣ Criar Funcionario com Validação
    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        validarFuncionario(funcionario);
        return funcionarioRepository.save(funcionario);
    }

    // 2️⃣ Listar todos os Funcionarios
    @Transactional(readOnly = true)
    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    // 3️⃣ Listar Funcionários com Paginação
    @Transactional(readOnly = true)
    public Page<Funcionario> listarPaginado(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    // 4️⃣ Buscar por ID
    @Transactional(readOnly = true)
    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário com ID " + id + " não encontrado"));
    }

    // 5️⃣ Atualizar Funcionario com Validação
    @Transactional
    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = buscarPorId(id);
        validarFuncionario(funcionarioAtualizado);

        funcionarioExistente.setPessoa(funcionarioAtualizado.getPessoa());
        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
        funcionarioExistente.setSalario(funcionarioAtualizado.getSalario());
        funcionarioExistente.setDataContratacao(funcionarioAtualizado.getDataContratacao());
        return funcionarioRepository.save(funcionarioExistente);
    }

    // 6️⃣ Deletar Funcionario
    @Transactional
    public void deletar(Long id) {
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }

    // 7️⃣ Buscar Funcionários por Cargo
    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorCargo(String cargo) {
        return funcionarioRepository.findByCargoIgnoreCase(cargo);
    }

    // 8️⃣ Buscar Funcionários com Salário Maior que X
    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorSalarioMaiorQue(BigDecimal salario) {
        return funcionarioRepository.findBySalarioGreaterThan(salario);
    }

    // 🔒 Método Privado para Validar Funcionario
    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getPessoa() == null || funcionario.getPessoa().getId() == null) {
            throw new IllegalArgumentException("Pessoa associada ao funcionário é obrigatória");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().isEmpty()) {
            throw new IllegalArgumentException("O cargo do funcionário é obrigatório");
        }
        if (funcionario.getSalario() == null || funcionario.getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O salário do funcionário deve ser maior que zero");
        }
        if (funcionario.getDataContratacao() == null) {
            throw new IllegalArgumentException("A data de contratação é obrigatória");
        }
    }
}
