package br.com.rezende.pedido_service.controller;

import br.com.rezende.pedido_service.dto.VendasVendedorDTO;
import br.com.rezende.pedido_service.model.Vendedor;
import br.com.rezende.pedido_service.service.vendedor.IVendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private IVendedorService service;
    @PostMapping("/")
    public ResponseEntity<Vendedor> criar(@Valid @RequestBody Vendedor novoVendedor) {
        Vendedor vendedorSalvo = service.criarVendedor(novoVendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorSalvo);
    }
    @GetMapping("/")
    public ResponseEntity<List<Vendedor>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> alterar(@PathVariable UUID id, @Valid @RequestBody Vendedor dadosVendedor) {
        Vendedor vendedorAtualizado = service.alterarVendedor(id, dadosVendedor);
        return ResponseEntity.ok(vendedorAtualizado);
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<VendasVendedorDTO>> relatorioVendas() {
        return ResponseEntity.ok(service.calcularVendasPorVendedor());
    }

    @PatchMapping("/{id}/demitir")
    public ResponseEntity<Vendedor> demitir(@PathVariable UUID id) {
        Vendedor vendedorDemitido = service.demitirVendedor(id);
        return ResponseEntity.ok(vendedorDemitido);
    }

    @PatchMapping("/{id}/readmitir")
    public ResponseEntity<Vendedor> readmitir(@PathVariable UUID id) {
        Vendedor vendedorReadmitido = service.readmitirVendedor(id);
        return ResponseEntity.ok(vendedorReadmitido);
    }
}
