package com.cafeteria.cafeteria.infrastructure.messaging;

import com.cafeteria.cafeteria.domain.model.PedidoRealizadoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PedidoEventConsumer.class);
    private final ObjectMapper objectMapper;

    public PedidoEventConsumer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void init() {
        log.info("🚀 Consumer Kafka iniciado — aguardando mensagens...");
    }

    @KafkaListener(
            topics = "pedidos-realizados",
            groupId = "cafeteria-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumir(byte[] message) {
        try {
            String json = new String(message, java.nio.charset.StandardCharsets.UTF_8);
            PedidoRealizadoEvent event = objectMapper.readValue(json, PedidoRealizadoEvent.class);
            log.info("📦 Pedido recebido: {}", event.pedidoId());
            log.info("🍽️  Mesa: {}", event.mesaId());
            log.info("💰 Total: R${}", event.total());
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage(), e);
        }
    }
}