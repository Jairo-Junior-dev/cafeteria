package com.cafeteria.cafeteria.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringPedidoRepository extends JpaRepository<PedidoEntity, UUID> {
}
