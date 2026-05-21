package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Pedido;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface RealizarPedidoUseCase {
    Pedido realizar(UUID mesaId, List<ItemRequest> itens);

    record ItemRequest(
            UUID produtoId,
            String nomeProduto,
            BigDecimal precoUnitario,
            Integer quantidade
    ) {}
}