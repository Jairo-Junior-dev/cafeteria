package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import com.cafeteria.cafeteria.domain.port.in.AtualizarStatusUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;

import java.util.UUID;

public class AtualizarStatusUseCaseImpl implements AtualizarStatusUseCase {


    private final PedidoRepository pedidoRepository;
    public AtualizarStatusUseCaseImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Pedido atualizar(UUID id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.buscarPorId(id).
                orElseThrow(()-> new DomainException("Pedido não encontrado!: + " +id));
        pedido.atualizarStatus(novoStatus);
        return pedidoRepository.salvar(pedido);
    }
}
