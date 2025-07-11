package br.com.rezende.pedido_service.service.pedido;

import br.com.rezende.pedido_service.client.ProdutoClient;
import br.com.rezende.pedido_service.dto.ProdutoDTO;
import br.com.rezende.pedido_service.model.PedidoItem;
import br.com.rezende.pedido_service.model.SituacaoPedido;
import br.com.rezende.pedido_service.model.Pedido;
import br.com.rezende.pedido_service.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements IpedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoClient produtoClient;


    @Override
    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // --- Geração do Número do Pedido ---
        LocalDate data = LocalDate.now();
        long totalPedidosDeHoje = repository.countByDataEmissao(data);
        long proximoNumero = totalPedidosDeHoje + 1;
        String pedidoData = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String pedidoNumero = String.format("%04d", proximoNumero);
        String numeroPedidoFormatado = pedidoData + "-" + pedidoNumero;

        // --- Registrando os dados no objeto ---
        pedido.setNumeroPedido(numeroPedidoFormatado);
        pedido.setDataEmissao(data);
        pedido.setSituacao(SituacaoPedido.PENDENTE);
        pedido.setId(null);

        // --- VALIDAÇÃO E ENRIQUECIMENTO DOS ITENS ---
        if (pedido.getItens() != null) {
            for (PedidoItem item : pedido.getItens()) {
                try {
                    // Para cada item, "ligamos" para o produto-service para buscar os detalhes
                    ProdutoDTO produtoInfo = produtoClient.buscarProdutoPorId(item.getProdutoId());
                    System.out.println("Produto encontrado: " + produtoInfo.getNome());

                    // Opcional: você pode usar o preço atual do catálogo em vez do que veio na requisição
                    item.setValorUnitario(produtoInfo.getValor());

                    // Garante a ligação bidirecional para o JPA
                    item.setPedido(pedido);

                } catch (Exception e) {
                    // Se o produto não for encontrado no produto-service, o Feign lança uma exceção.
                    // Aqui tratamos isso, impedindo a criação do pedido.
                    throw new EntityNotFoundException("Produto com ID " + item.getProdutoId() + " não encontrado no catálogo.");
                }
            }
        }

        return repository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido alterarPedido(UUID id, Pedido dadosPedido) {
        // Padrão "busque e atualize" para segurança
        Pedido pedidoDoBanco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));

        // Atualiza apenas os campos permitidos
        pedidoDoBanco.setDataEntrega(dadosPedido.getDataEntrega());
        pedidoDoBanco.setCliente(dadosPedido.getCliente());
        // Geralmente, os itens de um pedido são alterados por outros endpoints, não todos de uma vez

        return repository.save(pedidoDoBanco);
    }

    @Override
    @Transactional
    public Pedido alterarSituacao(UUID id, SituacaoPedido novaSituacao) {
        Pedido pedidoDoBanco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
        pedidoDoBanco.setSituacao(novaSituacao);
        return repository.save(pedidoDoBanco);
    }

    @Override
    public void apagarPedido(UUID id) {
        // ID do Pedido é Long, não UUID
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Pedido> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Pedido> buscarPorId(UUID id) {
        return repository.findById(id);
    }
}