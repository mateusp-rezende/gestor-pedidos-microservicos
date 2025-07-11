package br.com.rezende.pedido_service.client;

import br.com.rezende.pedido_service.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "PRODUTO-SERVICE") // O nome EXATO registrado no Eureka
public interface ProdutoClient {

    @GetMapping("/produtos/{id}") // O endpoint que queremos chamar
    ProdutoDTO buscarProdutoPorId(@PathVariable("id") UUID id);
}