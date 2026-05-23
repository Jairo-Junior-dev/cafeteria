package com.cafeteria.cafeteria;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.exception.TransicaoInvalidaException;
import com.cafeteria.cafeteria.domain.model.ItemPedido;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
public class PedidoTest {

    private ItemPedido criarItem() {
        return new ItemPedido(
                UUID.randomUUID(),
                "Café Espresso",
                BigDecimal.valueOf(8.00),
                2
        );
    }
    @Test
    void deveCriarPedidoComStatusCriado(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        assertEquals(StatusPedido.CRIADO, pedido.getStatus());
    }
    @Test
    void deveCalcularTotalCorretamente(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.adicionarItem(criarItem());
        assertEquals(BigDecimal.valueOf(16.0),pedido.calcularTotal());
    }

    @Test
    void deveLancarExcecaoAvancarSemItens(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        assertThrows(DomainException.class, ()->{
            pedido.atualizarStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        });
    }
    @Test
    void deveLancarExcecaoEmTransicaoInvalida(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.adicionarItem(criarItem());
        pedido.atualizarStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        assertThrows(TransicaoInvalidaException.class, ()->pedido.atualizarStatus(StatusPedido.ENTREGUE));
    }
    @Test
    void deveAtualizarStatusCorretamente(){
        Pedido pedido = new Pedido(UUID.randomUUID());
        pedido.adicionarItem(criarItem());
        pedido.atualizarStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        assertEquals(StatusPedido.AGUARDANDO_PAGAMENTO, pedido.getStatus());
    }
}
