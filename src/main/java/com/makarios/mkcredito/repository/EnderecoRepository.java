package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    // Adicione métodos personalizados, se necessário
}
