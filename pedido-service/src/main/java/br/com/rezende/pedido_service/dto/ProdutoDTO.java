package br.com.rezende.pedido_service.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class ProdutoDTO {
    private UUID id;
    private String nome;
    private Double valor;
}