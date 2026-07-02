package com.cafeteria.cafeteria.infrastructure.ratelimit;

import java.time.Duration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandAsyncExecutor;
import org.springframework.stereotype.Component;

import com.cafeteria.cafeteria.domain.port.out.RateLimiterPort;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.serialization.Mapper;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;

import jakarta.annotation.PostConstruct;

@Component
public class Bucket4jRateLimiter implements RateLimiterPort {

    // 1. campos primeiro
    private static final long CAPACITY       = 20L;
    private static final long REFILL_SECONDS = 60L;

    private final RedissonClient redissonClient;
    private RedissonBasedProxyManager<String> proxyManager;

    // 2. construtor
    public Bucket4jRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    // 3. inicialização pós-construção
    @PostConstruct
    public void init() {
        System.out.println(">>[RateLimit] init() chamado");
        CommandAsyncExecutor executor =
            ((Redisson) redissonClient).getCommandExecutor();

        this.proxyManager = RedissonBasedProxyManager
            .builderFor(executor)
            .withKeyMapper(Mapper.STRING)
            .withExpirationStrategy(
                ExpirationAfterWriteStrategy
                    .basedOnTimeForRefillingBucketUpToMax(
                        Duration.ofSeconds(REFILL_SECONDS + 10)
                    )
            )
            .build();
            System.out.println(">>[RateLimit] proxymanager criado:"+proxyManager);            
        }

    // 4. métodos
    private Bucket resolveBucket(String key) {
        BucketConfiguration config = BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                .capacity(CAPACITY)
                .refillGreedy(CAPACITY, Duration.ofSeconds(REFILL_SECONDS))
                .build())
            .build();

        return proxyManager.builder().build(key, () -> config);
    }

    @Override
    public boolean isAllowed(String key) {
        System.out.println(">>> [RateLimit] isAllowed chamado para key: " + key);
        return resolveBucket(key).tryConsume(1);
    }
@Override
public long getWaitTimeInSeconds(String key) {
        // CORRETO: Tenta consumir 1 token. Como o usuário já estourou o limite,
    // o método retornará falso e nos dará o tempo exato para o próximo refill.
    ConsumptionProbe probe = resolveBucket(key).tryConsumeAndReturnRemaining(1);
    
    if (probe.isConsumed()) {
        return 0L;
    }
    // Converte de nanossegundos para segundos
    return probe.getNanosToWaitForRefill() / 1_000_000_000L;
}

}