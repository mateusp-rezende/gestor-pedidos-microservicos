package br.com.rezende.pedido_service.repository;

import br.com.rezende.pedido_service.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

}
