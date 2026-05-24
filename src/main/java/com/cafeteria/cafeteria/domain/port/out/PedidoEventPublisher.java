package com.cafeteria.cafeteria.domain.port.out;

import com.cafeteria.cafeteria.domain.model.PedidoRealizadoEvent;

public interface PedidoEventPublisher
{
    void publicar(PedidoRealizadoEvent event);
}
