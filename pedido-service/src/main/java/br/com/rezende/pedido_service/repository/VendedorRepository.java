package br.com.rezende.pedido_service.repository;

import br.com.rezende.pedido_service.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VendedorRepository extends JpaRepository<Vendedor, UUID> {
    /**
     * Busca todos os vendedores e, na mesma consulta, já carrega (FETCH)
     * a lista de pedidos e os itens de cada pedido.
     * Isto resolve o problema de Lazy Loading em coleções aninhadas.
     * O "DISTINCT" evita que vendedores sejam duplicados no resultado por causa dos JOINs.
     */
    @Query("SELECT DISTINCT v FROM Vendedor v LEFT JOIN FETCH v.pedidos p LEFT JOIN FETCH p.itens")
    List<Vendedor> findAllWithPedidosAndItens();
}
