package br.com.rezende.pedido_service.repository;


import br.com.rezende.pedido_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    public Cliente findByTelefone(String telefone);
}
