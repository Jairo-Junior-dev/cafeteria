package com.cafeteria.cafeteria.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoRealizadoEvent(
                    UUID pedidoId,
                    UUID mesaId,
                    BigDecimal total,
                    LocalDateTime criadoEm
                                   ) {
    public static PedidoRealizadoEvent de(Pedido pedido){
        return new PedidoRealizadoEvent(pedido.getId(),
                pedido.getMesaId(),
                pedido.calcularTotal(),
                pedido.getCriadoEm());
    }

}
