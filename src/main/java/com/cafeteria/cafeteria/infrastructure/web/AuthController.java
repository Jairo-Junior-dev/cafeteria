package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.domain.model.Usuario;
import com.cafeteria.cafeteria.domain.port.in.LoginUseCase;
import com.cafeteria.cafeteria.domain.port.in.RegistrarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Registro e login de usuários")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                          LoginUseCase loginUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Operation(summary = "Registrar novo usuário")
    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@Valid @RequestBody RegistrarRequest request) {
        Usuario usuario = registrarUsuarioUseCase.registrar(
            new RegistrarUsuarioUseCase.RegistrarRequest(
                request.nome(),
                request.email(),
                request.senha(),
                request.tipo()
            )
        );
        return new UsuarioResponse(
            usuario.getId().toString(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTipo().name()
        );
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        String token = loginUseCase.login(
            new LoginUseCase.LoginRequest(request.email(), request.senha())
        );
        return new TokenResponse(token);
    }

    record RegistrarRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Tipo é obrigatório")
        com.cafeteria.cafeteria.domain.model.TipoUsuario tipo
    ) {}

    record LoginRequest(
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha
    ) {}

    record UsuarioResponse(
        String id,
        String nome,
        String email,
        String tipo
    ) {}

    record TokenResponse(String token) {}
}   