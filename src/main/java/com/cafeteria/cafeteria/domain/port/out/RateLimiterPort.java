package com.cafeteria.cafeteria.domain.port.out;

public interface RateLimiterPort {
      /**
     * Verifica se a chave (IP ou userId) está dentro do limite.
     *
     * @param key identificador do cliente
     * @return true se a requisição é permitida, false se bloqueada
     */
    boolean isAllowed(String key);

    /**
     * Quanto tempo (em segundos) até o próximo token ficar disponível.
     * Usado no header Retry-After da resposta 429.
     */
    long getWaitTimeInSeconds(String key);
}
