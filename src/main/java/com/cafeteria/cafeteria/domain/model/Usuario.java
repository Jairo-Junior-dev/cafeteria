package com.cafeteria.cafeteria.domain.model;
import com.cafeteria.cafeteria.domain.exception.DomainException;
import java.util.UUID;

public class Usuario {

    private final UUID id;
    private final String nome;
    private final String email;
    private final String senhaCriptografada;
    private final TipoUsuario tipo;
    public Usuario(UUID id, String nome, String email, String senhaCriptografada, TipoUsuario tipo) {
        if (nome == null || nome.isBlank()) {
            throw new DomainException("Nome é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new DomainException("Email é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaCriptografada = senhaCriptografada;
        this.tipo = tipo;
    }

    public static Usuario criar(String nome, String email, String senhaCriptografada, TipoUsuario tipo) {
        return new Usuario(UUID.randomUUID(), nome, email, senhaCriptografada, tipo);
    }

    public static Usuario reconstituir(UUID id, String nome, String email, String senhaCriptografada, TipoUsuario tipo) {
        return new Usuario(id, nome, email, senhaCriptografada, tipo);
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenhaCriptografada() { return senhaCriptografada; }
    public TipoUsuario getTipo() { return tipo; }
}