package com.cafeteria.cafeteria.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record Produto(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean disponivel
) implements Serializable {
}
