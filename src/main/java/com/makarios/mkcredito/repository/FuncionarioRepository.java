package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Page<Funcionario> findByPessoaId(Long pessoaId, Pageable pageable);

    // 🔍 Busca um funcionário pelo cargo (sem considerar maiúsculas ou minúsculas)
    List<Funcionario> findByCargoIgnoreCase(String cargo);

    // 🔍 Busca um funcionário pelo cargo usando "contains" (parte do texto)
    List<Funcionario> findByCargoContainingIgnoreCase(String cargo);

    // 🔍 Busca funcionários com salário maior que o valor informado
    List<Funcionario> findBySalarioGreaterThan(BigDecimal salario);

    // 🔍 Busca funcionários com salário entre dois valores
    List<Funcionario> findBySalarioBetween(BigDecimal min, BigDecimal max);

    // 🔍 Busca funcionários por data de contratação maior ou igual a uma data
    List<Funcionario> findByDataContratacaoAfter(java.time.LocalDate data);

    // 🔍 Busca funcionários com paginação e ordenação por qualquer campo
    Page<Funcionario> findByCargoContainingIgnoreCase(String cargo, Pageable pageable);
}
