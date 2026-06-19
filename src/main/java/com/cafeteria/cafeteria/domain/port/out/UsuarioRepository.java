package com.cafeteria.cafeteria.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.cafeteria.cafeteria.domain.model.Usuario;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(UUID id);
}
