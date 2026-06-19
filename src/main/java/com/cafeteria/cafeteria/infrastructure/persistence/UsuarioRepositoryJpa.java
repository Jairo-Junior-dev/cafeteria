package com.cafeteria.cafeteria.infrastructure.persistence;

import com.cafeteria.cafeteria.domain.model.Usuario;
import com.cafeteria.cafeteria.domain.port.out.UsuarioRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryJpa implements UsuarioRepository {

    private final SpringUsuarioRepository springUsuarioRepository;

    public UsuarioRepositoryJpa(SpringUsuarioRepository springUsuarioRepository) {
        this.springUsuarioRepository = springUsuarioRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario);
        UsuarioEntity salvo = springUsuarioRepository.save(entity);
        return salvo.toDomain();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return springUsuarioRepository.findByEmail(email)
                .map(UsuarioEntity::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return springUsuarioRepository.findById(id)
                .map(UsuarioEntity::toDomain);
    }
}