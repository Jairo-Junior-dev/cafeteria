package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.StatusPedido;

import java.util.UUID;

public interface AtualizarStatusUseCase {
    Pedido atualizar (UUID id, StatusPedido novoStatus);
}
