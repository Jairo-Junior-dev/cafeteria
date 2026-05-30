package com.cafeteria.cafeteria.domain.port.out;

import com.cafeteria.cafeteria.domain.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoCache {
void salvar(Produto produto);
Optional<Produto> buscarPorId(UUID id);
List<Produto> buscarTodos();
void remover(UUID id);
}
