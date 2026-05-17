package com.cafeteria.cafeteria.domain.port.out;

import com.cafeteria.cafeteria.domain.model.Pedido;

import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(UUID id);
}
