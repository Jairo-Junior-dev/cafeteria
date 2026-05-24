package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.model.ItemPedido;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.PedidoRealizadoEvent;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoEventPublisher;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;

import java.util.List;

public class RealizarPedidoUseCaseImpl implements RealizarPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoEventPublisher eventPublisher;
    public RealizarPedidoUseCaseImpl(PedidoRepository pedidoRepository , PedidoEventPublisher eventPublisher) {
        this.pedidoRepository = pedidoRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Pedido realizar(java.util.UUID mesaId, List<ItemRequest> itens) {
        Pedido pedido = new Pedido(mesaId);

        itens.forEach(item -> pedido.adicionarItem(
                new ItemPedido(
                        item.produtoId(),
                        item.nomeProduto(),
                        item.precoUnitario(),
                        item.quantidade()
                )
        ));

        pedido.atualizarStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        Pedido salvo =  pedidoRepository.salvar(pedido);
        eventPublisher.publicar(PedidoRealizadoEvent.de(salvo));
        return salvo;
    }
}