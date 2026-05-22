package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.domain.model.Pedido;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface BuscarPedidoUseCase {
    Pedido buscarPedido(UUID id);
}
