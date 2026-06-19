package com.cafeteria.cafeteria.domain.port.in;

public interface LoginUseCase {
    String login(LoginRequest request);

    record LoginRequest(
        String email,
        String senha
    ) {}
}