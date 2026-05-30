package com.cafeteria.cafeteria.infrastructure.config;

import com.cafeteria.cafeteria.application.usecase.*;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.port.in.AdicionarProdutoUseCase;
import com.cafeteria.cafeteria.domain.port.in.AtualizarStatusUseCase;
import com.cafeteria.cafeteria.domain.port.in.BuscarCardapioUseCase;
import com.cafeteria.cafeteria.domain.port.out.ProdutoCache;
import com.cafeteria.cafeteria.infrastructure.web.BuscarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.in.RealizarPedidoUseCase;
import com.cafeteria.cafeteria.domain.port.out.PedidoEventPublisher;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfiguration {

    @Bean
    public RealizarPedidoUseCase realizarPedidoUseCase(PedidoRepository pedidoRepository,
                                                       PedidoEventPublisher eventPublisher) {
        return new RealizarPedidoUseCaseImpl(pedidoRepository, eventPublisher);
    }

    @Bean
    public AtualizarStatusUseCase atualizarStatusUseCase(PedidoRepository pedidoRepository) {
        return new AtualizarStatusUseCaseImpl(pedidoRepository);
    }

    @Bean
    public BuscarPedidoUseCase buscarPedidoUseCase(PedidoRepository pedidoRepository) {
        return new BuscarPedidoUseCaseImpl(pedidoRepository);
    }

    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate(
            ProducerFactory<String, byte[]> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, byte[]> producerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        configs.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        configs.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        configs.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 10000);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "cafeteria-group");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory(
            ConsumerFactory<String, byte[]> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
    @Bean
    public BuscarCardapioUseCase buscarCardapioUseCase(ProdutoCache produtoCache){
        return new BuscarCardapioUseCaseIMPL(produtoCache);
    }
    @Bean
    public AdicionarProdutoUseCase adicionarProdutoUseCase(ProdutoCache produtoCache){
        return new AdicionarProdutoUseCaseImpl(produtoCache);
    }
}