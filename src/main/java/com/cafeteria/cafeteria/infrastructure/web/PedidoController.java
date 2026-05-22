package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.application.usecase.BuscarPedidoUseCaseImpl;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import com.cafeteria.cafeteria.domain.port.in.AtualizarStatusUseCase;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final RealizarPedidoUseCase realizarPedidoUseCase;
    private  final AtualizarStatusUseCase  atualizarStatusUseCase;
    private final  BuscarPedidoUseCase buscarPedidoUseCase;

    public PedidoController(RealizarPedidoUseCase realizarPedidoUseCase,
                            AtualizarStatusUseCase  atualizarStatusUseCase,
                            BuscarPedidoUseCase buscarPedidoUseCase) {
        this.realizarPedidoUseCase = realizarPedidoUseCase;
        this.atualizarStatusUseCase = atualizarStatusUseCase;
        this.buscarPedidoUseCase = buscarPedidoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse realizar(@RequestBody RealizarPedidoRequest request) {
        Pedido pedido = realizarPedidoUseCase.realizar(
            request.mesaId(),
            request.itens().stream()
                .map(i -> new RealizarPedidoUseCase.ItemRequest(
                    i.produtoId(),
                    i.nomeProduto(),
                    i.precoUnitario(),
                    i.quantidade()
                ))
                .toList()
        );

        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.calcularTotal());
    }
    @PutMapping("/{id}/status")
    public PedidoResponse atualizarStatus(@PathVariable UUID id,@RequestBody AtualizarStatusRequest  request) {
        Pedido pedido = atualizarStatusUseCase.atualizar(id, request.status());
        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.calcularTotal());
    }
    @GetMapping("/{id}")
    public PedidoResponse buscar(@PathVariable UUID id) {
        Pedido pedido = buscarPedidoUseCase.buscarPedido(id);
        return new PedidoResponse(pedido.getId(), pedido.getStatus().name(), pedido.calcularTotal());
    }

    record RealizarPedidoRequest(
        UUID mesaId,
        List<ItemRequest> itens
    ) {}

    record ItemRequest(
        UUID produtoId,
        String nomeProduto,
        java.math.BigDecimal precoUnitario,
        Integer quantidade
    ) {}

    record AtualizarStatusRequest(
            StatusPedido status
    ){}
    record PedidoResponse(
        UUID id,
        String status,
        java.math.BigDecimal total
    ) {}
}
