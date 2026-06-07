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
    public PaginaCardapio buscarTodos(int pagina,int tamanho) {
        List<Produto> produtos = produtoCache.buscarTodos();
        int totalItens = produtos.size();
        int totalDePaginas =(int)Math.ceil((double) totalItens/tamanho);
        List<Produto>itensDaPagina = produtos.stream().
                skip((long)pagina * tamanho ).
                limit(tamanho).
                toList();
        return new PaginaCardapio(itensDaPagina,
                pagina,
                totalDePaginas,
                totalItens,
                tamanho);
    }

    @Override
    public Produto buscarPorId(UUID id) {
        return produtoCache.buscarPorId(id).orElseThrow(()-> new DomainException("Produto não encontrado; "+id));
    }
}
