package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.model.Usuario;
import com.cafeteria.cafeteria.domain.port.in.LoginUseCase;
import com.cafeteria.cafeteria.domain.port.out.UsuarioRepository;
import com.cafeteria.cafeteria.infrastructure.config.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginUseCaseImpl implements LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginUseCaseImpl(UsuarioRepository usuarioRepository,
                            PasswordEncoder passwordEncoder,
                            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String login(LoginRequest request) {
        Usuario usuario = usuarioRepository.buscarPorEmail(request.email())
                .orElseThrow(() -> new DomainException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaCriptografada())) {
            throw new DomainException("Email ou senh    a inválidos");
        }

        return jwtService.gerarToken(usuario);
    }
}