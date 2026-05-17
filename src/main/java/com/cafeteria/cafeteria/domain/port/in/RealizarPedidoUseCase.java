package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Pedido;

import java.math.BigDecimal;
import java.util.UUID;

public interface RealizarPedidoUseCase {
    Pedido realizarPedido(Pedido pedido);
    record itemRequest(UUID produtoId, String nomeProduto, BigDecimal precoUnitario, Integer quantidade){}
}
