package com.cafeteria.cafeteria.application.usecase;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.in.BuscarCardapioUseCase;
import com.cafeteria.cafeteria.domain.port.out.ProdutoCache;

import java.util.List;
import java.util.UUID;

public class BuscarCardapioUseCaseIMPL implements BuscarCardapioUseCase {
   private final ProdutoCache produtoCache;
    public  BuscarCardapioUseCaseIMPL(ProdutoCache produtoCache) {
        this.produtoCache = produtoCache;
    }


    @Override
    public List<Produto> buscarTodos() {
        return produtoCache.buscarTodos();
    }

    @Override
    public Produto buscarPorId(UUID id) {
        return produtoCache.buscarPorId(id).orElseThrow(()-> new DomainException("Produto não encontrado; "+id));
    }
}
