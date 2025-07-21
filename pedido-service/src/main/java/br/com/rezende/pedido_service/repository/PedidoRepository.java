package br.com.rezende.pedido_service.repository;

import br.com.rezende.pedido_service.dto.VendasVendedorDTO;
import br.com.rezende.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    long countByDataEmissao(LocalDate dataEmissao);

    @Query("SELECT new br.com.rezende.pedido_service.dto.VendasVendedorDTO(p.vendedor.id, p.vendedor.nome, SUM(i.valorUnitario * i.quantidade), COUNT(DISTINCT p.id)) " +
            "FROM Pedido p JOIN p.itens i " +
            "WHERE p.vendedor IS NOT NULL " +
            "GROUP BY p.vendedor.id, p.vendedor.nome")
    List<VendasVendedorDTO> findVendasPorVendedor();
}
