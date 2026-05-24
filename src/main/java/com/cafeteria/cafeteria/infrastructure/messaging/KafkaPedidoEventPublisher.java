package com.cafeteria.cafeteria.infrastructure.messaging;

import com.cafeteria.cafeteria.domain.model.PedidoRealizadoEvent;
import com.cafeteria.cafeteria.domain.port.out.PedidoEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPedidoEventPublisher implements PedidoEventPublisher {

    private static final String TOPICO = "pedidos-realizados";

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaPedidoEventPublisher(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void publicar(PedidoRealizadoEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            byte[] bytes = json.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            kafkaTemplate.send(TOPICO, event.pedidoId().toString(), bytes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar evento", e);
        }
    }
}