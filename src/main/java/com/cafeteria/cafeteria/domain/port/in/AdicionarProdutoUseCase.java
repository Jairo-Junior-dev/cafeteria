package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Produto;

import java.math.BigDecimal;

public interface AdicionarProdutoUseCase {
    Produto adicionarProduto(ProdutoRequestAdd produtoRequestAdd);

    record ProdutoRequestAdd(String nome, String descricao, BigDecimal preco) {

    }
}
