package com.cafeteria.cafeteria;

import com.cafeteria.cafeteria.domain.model.ItemPedido;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.model.StatusPedido;
import com.cafeteria.cafeteria.domain.port.in.AtualizarStatusUseCase;
import com.cafeteria.cafeteria.domain.port.in.BuscarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.infrastructure.web.PedidoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PedidoIntegrationTest {

    @Mock
    private RealizarPedidoUseCase realizarPedidoUseCase;

    @Mock
    private AtualizarStatusUseCase atualizarStatusUseCase;

    @Mock
    private BuscarPedidoUseCase buscarPedidoUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new PedidoController(
                realizarPedidoUseCase,
                atualizarStatusUseCase,
                buscarPedidoUseCase
            ))
            .build();
    }

    private Pedido criarPedidoMock() {
        UUID mesaId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Pedido pedido = new Pedido(mesaId);
        pedido.adicionarItem(new ItemPedido(
            UUID.randomUUID(),
            "Café Espresso",
            BigDecimal.valueOf(8.00),
            2
        ));
        pedido.atualizarStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        return pedido;
    }

    @Test
    void deveCriarPedidoERetornar201() throws Exception {
        Pedido pedido = criarPedidoMock();
        when(realizarPedidoUseCase.realizar(any(), any())).thenReturn(pedido);

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "mesaId": "550e8400-e29b-41d4-a716-446655440000",
                        "itens": [
                            {
                                "produtoId": "550e8400-e29b-41d4-a716-446655440001",
                                "nomeProduto": "Café Espresso",
                                "precoUnitario": 8.00,
                                "quantidade": 2
                            }
                        ]
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("AGUARDANDO_PAGAMENTO"))
                .andExpect(jsonPath("$.total").value(16.0));
    }

    @Test
    void deveRetornar400QuandoPedidoSemItens() throws Exception {
        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "mesaId": "550e8400-e29b-41d4-a716-446655440000",
                        "itens": []
                    }
                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarPedidoPorId() throws Exception {
        Pedido pedido = criarPedidoMock();
        when(buscarPedidoUseCase.buscarPedido(any())).thenReturn(pedido);

        mockMvc.perform(get("/pedidos/" + pedido.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    void deveAtualizarStatusDoPedido() throws Exception {
        Pedido pedido = criarPedidoMock();
        pedido.atualizarStatus(StatusPedido.PAGO);
        when(atualizarStatusUseCase.atualizar(any(), any())).thenReturn(pedido);

        mockMvc.perform(put("/pedidos/" + pedido.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"status": "PAGO"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAGO"));
    }
}