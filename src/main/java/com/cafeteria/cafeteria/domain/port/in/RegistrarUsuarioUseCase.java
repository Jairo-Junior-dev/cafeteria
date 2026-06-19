package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.TipoUsuario;
import com.cafeteria.cafeteria.domain.model.Usuario;

public  interface  RegistrarUsuarioUseCase {
    Usuario registrar(RegistrarRequest      request);

    record RegistrarRequest(String nome,
        String email,
        String senha,
        TipoUsuario tipo
    ){}

}
