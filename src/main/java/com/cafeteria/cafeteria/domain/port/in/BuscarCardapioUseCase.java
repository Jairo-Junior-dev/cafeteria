package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Produto;

import java.util.List;
import java.util.UUID;

public interface BuscarCardapioUseCase {
    List<Produto> buscarTodos();
    Produto buscarPorId(UUID id);



}
