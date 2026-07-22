package com.cafeteria.cafeteria.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.cafeteria.cafeteria.domain.model.Pedido;

public interface PedidoRepository {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(UUID id);
    
}
