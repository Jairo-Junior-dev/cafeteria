package com.cafeteria.cafeteria.infrastructure.config;

import com.cafeteria.cafeteria.application.usecase.RealizarPedidoUseCaseImpl;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RealizarPedidoUseCase realizarPedidoUseCase(PedidoRepository pedidoRepository) {
        return new RealizarPedidoUseCaseImpl(pedidoRepository);
    }
}
