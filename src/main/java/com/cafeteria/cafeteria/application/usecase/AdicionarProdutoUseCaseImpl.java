package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.in.AdicionarProdutoUseCase;
import com.cafeteria.cafeteria.domain.port.out.ProdutoCache;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;

import java.util.UUID;

public class AdicionarProdutoUseCaseImpl implements AdicionarProdutoUseCase {
    private final ProdutoCache produtoCache;
    public AdicionarProdutoUseCaseImpl(ProdutoCache produtoCache) {
        this.produtoCache = produtoCache;
    }
    @Override
    public Produto adicionarProduto(ProdutoRequestAdd produtoRequestAdd) {
        Produto produto = new Produto(
                UUID.randomUUID(),
                produtoRequestAdd.nome(),
                produtoRequestAdd.descricao(),
                produtoRequestAdd.preco(),
                true
        );
         this.produtoCache.salvar(produto); ;
        return produto;
    }
}
