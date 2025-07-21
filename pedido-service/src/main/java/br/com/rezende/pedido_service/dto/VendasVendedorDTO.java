package br.com.rezende.pedido_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendasVendedorDTO {

    private UUID vendedorId;
    private String nomeVendedor;
    private Double totalVendido;
    private Long quantidadePedidos;

}
