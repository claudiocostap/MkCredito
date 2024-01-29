package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}