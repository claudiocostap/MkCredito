package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Método para encontrar uma pessoa pelo CPF
    Optional<Pessoa> findByCpf(String cpf);

    // Método para buscar por nome (ou parte do nome)
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
}
