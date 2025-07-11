package br.com.rezende.produto_service.services;

import br.com.rezende.produto_service.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoService {

    Produto criarProduto(Produto produto);
    Produto alterarProduto(UUID id, Produto produto);
    void apagarProduto(UUID id);
    List<Produto> buscarTodos();
    Optional<Produto> buscarPorId(UUID id);
}
