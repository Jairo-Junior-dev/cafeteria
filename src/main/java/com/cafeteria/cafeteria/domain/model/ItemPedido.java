package com.cafeteria.cafeteria.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemPedido {
private UUID id;
private String nomeProduto;
private BigDecimal precoUnitario;
private Integer quantidade;

public ItemPedido(UUID id, String nomeProduto, BigDecimal precoUnitario, Integer quantidade) {
    this.id = id;
    this.nomeProduto = nomeProduto;
    this.precoUnitario = precoUnitario;
    this.quantidade = quantidade;
}
public BigDecimal calcularSubTotal() {
    return precoUnitario.multiply( BigDecimal.valueOf(quantidade));
}
    public UUID getProdutoId() {
        return id;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
}
