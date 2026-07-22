package com.cafeteria.cafeteria.domain.port.out.metrics;

import java.util.concurrent.TimeUnit;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

public class MetricsPortUseCase implements MetricsPort {
    private final MeterRegistry meterRegistry;

    public MetricsPortUseCase(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void incrementarContador(String nome, String... tags) {
        this.meterRegistry.counter(nome, tags).increment();
    }

    @Override
    public void registrarTempo(String nome, long milissegundos, String... tags) {
        Timer.builder(nome).
        tags(tags).
        register(meterRegistry).
        record(milissegundos,TimeUnit.MILLISECONDS);
        
    
    }

}
