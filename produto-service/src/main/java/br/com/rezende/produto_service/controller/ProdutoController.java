package br.com.rezende.produto_service.controller;

import br.com.rezende.produto_service.model.Produto;
import br.com.rezende.produto_service.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping("/")
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto novoProduto) {
        Produto produtoSalvo = service.criarProduto(novoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }


    @GetMapping("/")
    public ResponseEntity<List<Produto>> buscarTodos() {
        List<Produto> produtos = service.buscarTodos();
        return ResponseEntity.ok(produtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable UUID id) {
        return service.buscarPorId(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Produto> alterar(@PathVariable UUID id, @Valid @RequestBody Produto dadosProduto) {
        Produto produtoAtualizado = service.alterarProduto(id, dadosProduto);
        return ResponseEntity.ok(produtoAtualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        service.apagarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
