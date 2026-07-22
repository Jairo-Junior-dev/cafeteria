package com.cafeteria.cafeteria.application.usecase;

import java.util.UUID;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.in.AdicionarProdutoUseCase;
import com.cafeteria.cafeteria.domain.port.out.ProdutoCache;
import com.cafeteria.cafeteria.domain.port.out.ProdutoRepository;

public class AdicionarProdutoUseCaseImpl implements AdicionarProdutoUseCase {
    private final ProdutoCache produtoCache;
    private final ProdutoRepository produtoRepository;
    public AdicionarProdutoUseCaseImpl(ProdutoCache produtoCache, ProdutoRepository produtoRepository) {
        this.produtoCache = produtoCache;
        this.produtoRepository = produtoRepository;
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
    Produto salvo =    this.produtoRepository.salvar(produto); ;
    this.produtoCache.salvar(salvo);    
    return salvo;
    }
}
