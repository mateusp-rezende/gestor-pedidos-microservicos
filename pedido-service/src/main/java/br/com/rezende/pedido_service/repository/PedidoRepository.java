package br.com.rezende.pedido_service.repository;

import br.com.rezende.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    long countByDataEmissao(LocalDate dataEmissao);
}
