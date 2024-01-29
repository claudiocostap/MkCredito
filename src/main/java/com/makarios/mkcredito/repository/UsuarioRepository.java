package com.makarios.mkcredito.repository;

import com.makarios.mkcredito.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}