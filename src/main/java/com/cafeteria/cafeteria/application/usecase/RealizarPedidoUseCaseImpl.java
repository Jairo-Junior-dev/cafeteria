package com.cafeteria.cafeteria.application.usecase;

import java.util.List;
import java.util.UUID;

import com.cafeteria.cafeteria.domain.model.ItemPedido;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.PedidoRealizadoEvent;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoEventPublisher;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import com.cafeteria.cafeteria.domain.port.out.metrics.MetricsPort;

public class RealizarPedidoUseCaseImpl implements RealizarPedidoUseCase {

    private final MetricsPort metricsPort;
    private final PedidoRepository pedidoRepository;
    private final PedidoEventPublisher eventPublisher;

    public RealizarPedidoUseCaseImpl(
            PedidoRepository pedidoRepository,
            PedidoEventPublisher eventPublisher,
            MetricsPort metricsPort
    ) {
        this.pedidoRepository = pedidoRepository;
        this.eventPublisher = eventPublisher;
        this.metricsPort = metricsPort;
    }

    @Override
    public Pedido realizar(UUID mesaId, List<ItemRequest> itens) {
        long inicio = System.currentTimeMillis();

        try {
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

            Pedido salvo = pedidoRepository.salvar(pedido);
            eventPublisher.publicar(PedidoRealizadoEvent.de(salvo));

            metricsPort.incrementarContador("cafeteria_pedidos_criados_total",
                    "status", salvo.getStatus().name());

            salvo.getItens().forEach(item ->
                metricsPort.incrementarContador("cafeteria_item_vendidos_total",
                        "produto_id", item.getProdutoId().toString(),
                        "produto_nome", item.getNomeProduto())
            );

            return salvo;

        } catch (Exception e) {
            metricsPort.incrementarContador("cafeteria_pedidos_falha_total",
                    "motivo", e.getClass().getSimpleName());
            throw e;

        } finally {
            long duracao = System.currentTimeMillis() - inicio;
            metricsPort.registrarTempo("cafeteria_pedido_duracao_ms", duracao,
                    "operacao", "realizar");
        }
    }
}