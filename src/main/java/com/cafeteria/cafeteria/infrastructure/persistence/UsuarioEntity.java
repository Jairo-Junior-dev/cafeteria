package com.cafeteria.cafeteria.infrastructure.persistence;

import java.util.UUID;

import com.cafeteria.cafeteria.domain.model.TipoUsuario;
import com.cafeteria.cafeteria.domain.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {
    @Id
    private UUID id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    protected UsuarioEntity(){}

    public UsuarioEntity(UUID id ,
        String nome ,
        String email,
        String senha,
        TipoUsuario tipo){
        
        this.tipo = tipo;
        this.email = email;
        this.id = id;
        this.senha =senha;
        this.nome = nome;
    }

    public static UsuarioEntity fromDomain(Usuario usuario){
        return new UsuarioEntity(
        usuario.getId(),
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getSenhaCriptografada(),
        usuario.getTipo());
    }
    public  Usuario toDomain(){
         return Usuario.reconstituir(id,
            nome,
            email,
            senha, 
            tipo);
    }
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public TipoUsuario getTipo() { return tipo; }


}
