package br.com.rezende.produto_service.repository;

import br.com.rezende.produto_service.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
