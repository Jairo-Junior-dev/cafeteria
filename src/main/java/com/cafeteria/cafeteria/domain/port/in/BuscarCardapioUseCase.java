package com.cafeteria.cafeteria.domain.port.in;

import com.cafeteria.cafeteria.domain.model.Produto;

import java.util.List;
import java.util.UUID;

public interface BuscarCardapioUseCase {
    Produto buscarPorId(UUID id);
    PaginaCardapio buscarTodos(int pagina,int tamanho);

    record PaginaCardapio(
            List<Produto>produtos,
            int paginaAtual,
            int totalPaginas,
            long totalItens,
            int itensPorPagina

    ){}
}
