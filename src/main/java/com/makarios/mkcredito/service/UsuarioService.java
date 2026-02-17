package com.makarios.mkcredito.service;

import com.makarios.mkcredito.dto.LoginRequest;
import com.makarios.mkcredito.exceptionhandler.BusinessException;
import com.makarios.mkcredito.exceptionhandler.UsuarioNotFoundException;
import com.makarios.mkcredito.model.Usuario;
import com.makarios.mkcredito.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        validarUsuario(usuario);
        // TODO: Hash password before saving
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);
        validarUsuario(usuarioAtualizado, id);

        usuarioExistente.setPessoa(usuarioAtualizado.getPessoa());
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            // TODO: Hash password before saving
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        }
        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        validarUsuario(usuario, null);
    }

    private void validarUsuario(Usuario usuario, Long id) {
        if (usuario.getPessoa() == null || usuario.getPessoa().getId() == null) {
            throw new BusinessException("Pessoa associada ao usuário é obrigatória");
        }
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new BusinessException("O nome do usuário é obrigatório");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new BusinessException("O e-mail do usuário é obrigatório");
        }
        Optional<Usuario> usuarioComMesmoEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioComMesmoEmail.isPresent() && (id == null || usuarioComMesmoEmail.get().getId() != id)) {
            throw new BusinessException("E-mail já está sendo utilizado por outro usuário.");
        }
    }

    @Transactional(readOnly = true)
    public Usuario login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BusinessException("Usuário ou senha inválidos"));

        // TODO: Use a proper password encoder
        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new BusinessException("Usuário ou senha inválidos");
        }

        return usuario;
    }
}
