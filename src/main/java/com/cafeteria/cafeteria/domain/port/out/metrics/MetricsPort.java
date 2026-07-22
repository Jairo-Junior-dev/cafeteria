package com.cafeteria.cafeteria.domain.port.out.metrics;



public interface MetricsPort {
    void incrementarContador(String nome,String... tags);
    void registrarTempo(String nome, long milissegundos,String... tags);
}
