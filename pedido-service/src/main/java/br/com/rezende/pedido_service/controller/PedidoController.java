package br.com.rezende.pedido_service.controller;

import br.com.rezende.pedido_service.model.Pedido;
import br.com.rezende.pedido_service.model.SituacaoPedido;
import br.com.rezende.pedido_service.service.pedido.IpedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private IpedidoService service;

    @PostMapping("/")
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido novoPedido) {
        Pedido pedidoSalvo = service.criarPedido(novoPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    @GetMapping("/")
    public ResponseEntity<List<Pedido>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> alterar(@PathVariable UUID id, @Valid @RequestBody Pedido dadosPedido) {
        return ResponseEntity.ok(service.alterarPedido(id, dadosPedido));
    }


    @PatchMapping("/{id}/situacao")
    public ResponseEntity<Pedido> alterarSituacao(@PathVariable UUID id, @RequestBody SituacaoPedido novaSituacao) {
        return ResponseEntity.ok(service.alterarSituacao(id, novaSituacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        service.apagarPedido(id);
        return ResponseEntity.noContent().build();
    }
}