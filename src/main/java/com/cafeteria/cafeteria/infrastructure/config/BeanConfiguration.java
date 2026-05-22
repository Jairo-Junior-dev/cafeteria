package com.cafeteria.cafeteria.infrastructure.config;

import com.cafeteria.cafeteria.application.usecase.AtualizarStatusUseCaseImpl;
import com.cafeteria.cafeteria.application.usecase.BuscarPedidoUseCaseImpl;
import com.cafeteria.cafeteria.application.usecase.RealizarPedidoUseCaseImpl;
import com.cafeteria.cafeteria.domain.port.in.AtualizarStatusUseCase;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import com.cafeteria.cafeteria.infrastructure.web.BuscarPedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RealizarPedidoUseCase realizarPedidoUseCase(PedidoRepository pedidoRepository)
    {
        return new RealizarPedidoUseCaseImpl(pedidoRepository);
    }
    @Bean
    public AtualizarStatusUseCase  atualizarStatusUseCase(PedidoRepository pedidoRepository)
    {
        return new AtualizarStatusUseCaseImpl(pedidoRepository);
    }
    @Bean
    public BuscarPedidoUseCase buscarPedidoUseCase(PedidoRepository pedidoRepository) {
        return new BuscarPedidoUseCaseImpl(pedidoRepository);
    }

}
