package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    // Busca um funcionário por cargo
    List<Funcionario> findByCargo(String cargo);

    // Busca todos os funcionários ativos
    List<Funcionario> findByAtivo(Boolean ativo);
}