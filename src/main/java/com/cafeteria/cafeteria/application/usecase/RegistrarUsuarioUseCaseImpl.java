package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.model.Usuario;
import com.cafeteria.cafeteria.domain.port.in.RegistrarUsuarioUseCase;
import com.cafeteria.cafeteria.domain.port.out.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegistrarUsuarioUseCaseImpl implements RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository,
                                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(RegistrarRequest request) {
        usuarioRepository.buscarPorEmail(request.email())
                .ifPresent(u -> {
                    throw new DomainException("Email já cadastrado: " + request.email());
                });

        String senhaCriptografada = passwordEncoder.encode(request.senha());

        Usuario usuario = Usuario.  criar(
                request.nome(),
                request.email(),
                senhaCriptografada,
                request.tipo()
        );

        return usuarioRepository.salvar(usuario);
    }
}