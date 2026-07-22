package com.cafeteria.cafeteria.infrastructure.persistence;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.out.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaProdutoRepository implements ProdutoRepository {

    private final SpringProdutoRepository springProdutoRepository;

    public JpaProdutoRepository(SpringProdutoRepository springProdutoRepository) {
        this.springProdutoRepository = springProdutoRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        ProdutoEntity entity = toEntity(produto);
        ProdutoEntity salvo = springProdutoRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return springProdutoRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Produto> buscarTodos() {
        return springProdutoRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private ProdutoEntity toEntity(Produto produto) {
        return new ProdutoEntity(
                produto.id(),
                produto.nome(),
                produto.descricao(),
                produto.preco(),
                produto.disponivel()
        );
    }

    private Produto toDomain(ProdutoEntity entity) {
        return new Produto(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.isDisponivel()
        );
    }
}