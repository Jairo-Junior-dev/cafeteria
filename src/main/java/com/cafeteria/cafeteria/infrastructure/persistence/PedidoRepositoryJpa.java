package com.cafeteria.cafeteria.infrastructure.persistence;

import com.cafeteria.cafeteria.domain.model.ItemPedido;
import com.cafeteria.cafeteria.domain.model.Pedido;
import com.cafeteria.cafeteria.domain.port.out.PedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PedidoRepositoryJpa implements PedidoRepository {

    private final SpringPedidoRepository springRepository;

    public PedidoRepositoryJpa(SpringPedidoRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity entity = toEntity(pedido);
        PedidoEntity saved = springRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return springRepository.findById(id).map(this::toDomain);
    }

    private PedidoEntity toEntity(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();
        entity.setId(pedido.getId());
        entity.setMesaId(pedido.getMesaId());
        entity.setStatus(pedido.getStatus());
        entity.setCriadoEm(pedido.getCriadoEm());

        pedido.getItens().forEach(item -> {
            ItemPedidoEntity itemEntity = new ItemPedidoEntity();
            itemEntity.setProdutoId(item.getProdutoId());
            itemEntity.setNomeProduto(item.getNomeProduto());
            itemEntity.setPrecoUnitario(item.getPrecoUnitario());
            itemEntity.setQuantidade(item.getQuantidade());
            itemEntity.setPedido(entity);
            entity.getItens().add(itemEntity);
        });

        return entity;
    }

    private Pedido toDomain(PedidoEntity entity) {
        Pedido pedido = new Pedido(entity.getMesaId());
        entity.getItens().forEach(item ->
            pedido.adicionarItem(new ItemPedido(
                item.getProdutoId(),
                item.getNomeProduto(),
                item.getPrecoUnitario(),
                item.getQuantidade()
            ))
        );
        return pedido;
    }
}
