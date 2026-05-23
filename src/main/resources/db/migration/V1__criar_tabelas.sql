CREATE TABLE pedidos (
                         id UUID PRIMARY KEY,
                         mesa_id UUID NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         criado_em TIMESTAMP NOT NULL
);

CREATE TABLE itens_pedido (
                              id UUID PRIMARY KEY,
                              pedido_id UUID NOT NULL REFERENCES pedidos(id),
                              produto_id UUID NOT NULL,
                              nome_produto VARCHAR(255) NOT NULL,
                              preco_unitario DECIMAL(10,2) NOT NULL,
                              quantidade INTEGER NOT NULL
);