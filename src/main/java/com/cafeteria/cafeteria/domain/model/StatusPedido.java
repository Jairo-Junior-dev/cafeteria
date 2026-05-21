package com.cafeteria.cafeteria.domain.model;

public enum StatusPedido {
    CRIADO,
    AGUARDANDO_PAGAMENTO,
    PAGO,
    ACEITO,
    RECUSADO,
    EM_PREPARO,
    PRONTO,
    ENTREGUE,
    CANCELADO;

    public boolean podeTransitarPara(StatusPedido novo) {
        return switch (this){
            case CRIADO -> novo == AGUARDANDO_PAGAMENTO|| novo == CANCELADO;
            case AGUARDANDO_PAGAMENTO -> novo == PAGO|| novo == CANCELADO;
            case PAGO ->  novo == ACEITO || novo == RECUSADO;
            case ACEITO -> novo == EM_PREPARO;
            case EM_PREPARO ->  novo == PRONTO;
            case PRONTO ->  novo == ENTREGUE;
            default -> false;
        };
    }
}
