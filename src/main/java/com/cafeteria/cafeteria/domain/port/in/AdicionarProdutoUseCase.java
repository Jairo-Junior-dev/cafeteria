package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public interface AdicionarProdutoUseCase {
    Produto adicionarProduto(ProdutoRequestAdd produtoRequestAdd);

    record ProdutoRequestAdd(
            @NotBlank(message = "Nome é obrigatório")
            String nome,
            @NotBlank(message = "Descrição é obrigatória")
                             String descricao,
                             @NotNull(message = "Preço é obrigatório")
            @Positive(message="Preço deve ser maior que zero")
            BigDecimal preco) {

    }
}
