package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import com.cafeteria.cafeteria.infrastructure.web.BuscarPedidoUseCase;

import java.util.UUID;

public class BuscarPedidoUseCaseImpl implements BuscarPedidoUseCase {
    private final PedidoRepository pedidoRepository;
    public BuscarPedidoUseCaseImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }


    @Override
    public Pedido buscarPedido(UUID id) {
        return pedidoRepository.buscarPorId(id).
                orElseThrow(()->
                        new DomainException("Pedido não encontrado: "+id));
    }

}
