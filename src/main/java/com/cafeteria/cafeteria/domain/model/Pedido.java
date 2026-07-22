package com.cafeteria.cafeteria.domain.model;

import com.cafeteria.cafeteria.domain.exception.DomainException;
import com.cafeteria.cafeteria.domain.exception.TransicaoInvalidaException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Pedido {
private UUID id;
private UUID mesaUId;
private List<ItemPedido> itens;
private StatusPedido status;
private LocalDateTime criadoEm;
public Pedido(UUID mesaUId){
    this.id = UUID.randomUUID();
    this.mesaUId = mesaUId;
    this.itens = new ArrayList<>();
    this.status = StatusPedido.CRIADO;
    this.criadoEm = LocalDateTime.now();
}
    public void adicionarItem(ItemPedido item){
    if (item == null){throw  new DomainException("Item não pode ser nulo.");}
    this.itens.add(item);
    }
    public BigDecimal calcularTotal(){
        return itens.stream().map(
                ItemPedido::calcularSubTotal).
                reduce(BigDecimal.ZERO,
                BigDecimal::add);
    }
    public void atualizarStatus(StatusPedido novo){
    if (novo ==StatusPedido.AGUARDANDO_PAGAMENTO && itens.isEmpty()){
        throw  new DomainException("Pedido não pode ser confirmado sem itens");
    }
    if (!this.status.podeTransitarPara(novo)) {
        throw  new TransicaoInvalidaException(this.status,novo);
    }
    this.status = novo;
}
    public static Pedido reconstituir(UUID id, UUID mesaId,
                                      StatusPedido status,
                                      LocalDateTime criadoEm) {
        Pedido pedido = new Pedido(mesaId);
        pedido.id = id;
        pedido.status = status;
        pedido.criadoEm = criadoEm;
        return pedido;
    }
    public UUID getId() {
        return id;
    }

    public UUID getMesaId() {
        return mesaUId;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public StatusPedido getStatus() {
        return status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}
