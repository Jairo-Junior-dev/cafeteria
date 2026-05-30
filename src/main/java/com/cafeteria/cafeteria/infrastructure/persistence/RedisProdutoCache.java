package com.cafeteria.cafeteria.infrastructure.persistence;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.out.ProdutoCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RedisProdutoCache implements ProdutoCache {
   private static final String PREFIX = "produto:";
   private static final Duration TTL = Duration.ofSeconds(30);
   private final RedisTemplate<String, Object> redisTemplate;

   public RedisProdutoCache(RedisTemplate<String, Object> redisTemplate) {
       this.redisTemplate = redisTemplate;
   }


    @Override
    public void salvar(Produto produto) {
        redisTemplate.opsForValue().set(PREFIX+produto.id(), produto, TTL);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        Produto produto = (Produto) redisTemplate.opsForValue().get(PREFIX+id);
        return Optional.ofNullable(produto);
    }

    @Override   
    public List<Produto> buscarTodos() {
        return redisTemplate.keys(PREFIX+"*").
                stream().
                map(key->(Produto)redisTemplate.opsForValue().get(key)).toList();
    }

    @Override
    public void remover(UUID id) {
        redisTemplate.delete(PREFIX+id);
    }
}
