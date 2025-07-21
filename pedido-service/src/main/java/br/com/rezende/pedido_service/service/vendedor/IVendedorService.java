package br.com.rezende.pedido_service.service.vendedor;

import br.com.rezende.pedido_service.dto.VendasVendedorDTO;
import br.com.rezende.pedido_service.model.Vendedor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IVendedorService {
    Vendedor criarVendedor(Vendedor vendedor);

    Vendedor alterarVendedor(UUID id, Vendedor dadosVendedor);

    Vendedor demitirVendedor(UUID id);

    Vendedor readmitirVendedor(UUID id);

    List<Vendedor> buscarTodos();

    Optional<Vendedor> buscarPorId(UUID id);

    List<VendasVendedorDTO> calcularVendasPorVendedor();
}
