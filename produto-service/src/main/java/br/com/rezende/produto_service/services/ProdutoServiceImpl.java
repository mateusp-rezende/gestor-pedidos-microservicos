package br.com.rezende.produto_service.services;


import br.com.rezende.produto_service.model.Produto;
import br.com.rezende.produto_service.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Override
    public Produto criarProduto(Produto dadosProduto) {
        // Garante que é uma criação, forçando o ID a ser nulo.
        dadosProduto.setId(null);
        return repository.save(dadosProduto);
    }

    @Override
    public Produto alterarProduto(UUID id, Produto dadosProduto) {
        // Padrão "busque, altere e salve" para garantir a atualização correta.
        Produto produtoDoBanco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        // Atualiza os dados do objeto encontrado com os novos dados
        produtoDoBanco.setNome(dadosProduto.getNome());
        produtoDoBanco.setTipo(dadosProduto.getTipo());
        produtoDoBanco.setUnidadeMedida(dadosProduto.getUnidadeMedida());
        produtoDoBanco.setValor(dadosProduto.getValor());
        produtoDoBanco.setPrecoDeCusto(dadosProduto.getPrecoDeCusto());
        produtoDoBanco.setDescricao(dadosProduto.getDescricao());

        return repository.save(produtoDoBanco);
    }

    @Override
    public void apagarProduto(UUID id) {
        // Verifica a existência antes de deletar para ser mais seguro.
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Produto> buscarPorId(UUID id) {
        return repository.findById(id);
    }
}