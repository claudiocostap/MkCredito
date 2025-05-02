package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // 🔹 Buscar uma pessoa pelo CPF
    Optional<Pessoa> findByCpf(String cpf);

    // 🔹 Buscar pessoas pelo nome (ou parte do nome) com suporte a paginação
    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
