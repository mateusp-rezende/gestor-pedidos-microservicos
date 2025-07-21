package br.com.rezende.pedido_service.service.pedido;

import br.com.rezende.pedido_service.client.ProdutoClient;
import br.com.rezende.pedido_service.dto.ProdutoDTO;
import br.com.rezende.pedido_service.model.*;
import br.com.rezende.pedido_service.repository.ClienteRepository;
import br.com.rezende.pedido_service.repository.PedidoRepository;
import br.com.rezende.pedido_service.repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PedidoServiceImpl implements IpedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ProdutoClient produtoClient;

    @Override
    @Transactional
    public Pedido criarPedido(Pedido dadosPedido) {
        // 1. Cria um objeto Pedido novo e limpo.
        Pedido novoPedido = new Pedido();

        // 2. Busca e associa as entidades "reais" (managed).
        if (dadosPedido.getCliente() == null || dadosPedido.getCliente().getId() == null) {
            throw new IllegalArgumentException("O cliente do pedido é obrigatório.");
        }
        Cliente cliente = clienteRepository.findById(dadosPedido.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + dadosPedido.getCliente().getId()));
        novoPedido.setCliente(cliente);

        if (dadosPedido.getVendedor() != null && dadosPedido.getVendedor().getId() != null) {
            Vendedor vendedor = vendedorRepository.findById(dadosPedido.getVendedor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + dadosPedido.getVendedor().getId()));
            novoPedido.setVendedor(vendedor);
        }

        // 3. Define os dados do próprio pedido.
        LocalDate data = LocalDate.now();
        long totalPedidosDeHoje = pedidoRepository.countByDataEmissao(data);
        long proximoNumero = totalPedidosDeHoje + 1;
        String pedidoData = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String pedidoNumero = String.format("%04d", proximoNumero);
        novoPedido.setNumeroPedido(pedidoData + "-" + pedidoNumero);
        novoPedido.setDataEmissao(data);
        novoPedido.setDataEntrega(dadosPedido.getDataEntrega());
        novoPedido.setSituacao(SituacaoPedido.PENDENTE);

        // 4. Cria e associa os ITENS do zero.
        if (dadosPedido.getItens() != null && !dadosPedido.getItens().isEmpty()) {
            Set<PedidoItem> novosItens = new HashSet<>();
            for (PedidoItem itemDaRequisicao : dadosPedido.getItens()) {
                ProdutoDTO produtoInfo = produtoClient.buscarProdutoPorId(itemDaRequisicao.getProdutoId());

                PedidoItem novoItem = new PedidoItem(); // Cria um novo item
                novoItem.setProdutoId(produtoInfo.getId());
                novoItem.setQuantidade(itemDaRequisicao.getQuantidade());
                novoItem.setValorUnitario(produtoInfo.getValor());
                novoItem.setPedido(novoPedido); // Associa o item ao novo pedido

                novosItens.add(novoItem);
            }
            novoPedido.setItens(novosItens);
        }

        // 5. Salva o novo pedido. O JPA agora vai gerir a cascata corretamente.
        return pedidoRepository.save(novoPedido);
    }

    @Override
    @Transactional
    public Pedido alterarPedido(UUID id, Pedido dadosPedido) {
        // Padrão "busque e atualize" para segurança
        Pedido pedidoDoBanco = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));

        // Atualiza apenas os campos permitidos
        pedidoDoBanco.setDataEntrega(dadosPedido.getDataEntrega());
        pedidoDoBanco.setCliente(dadosPedido.getCliente());
        // Geralmente, os itens de um pedido são alterados por outros endpoints, não todos de uma vez

        return pedidoRepository.save(pedidoDoBanco);
    }

    @Override
    @Transactional
    public Pedido alterarSituacao(UUID id, SituacaoPedido novaSituacao) {
        Pedido pedidoDoBanco = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
        pedidoDoBanco.setSituacao(novaSituacao);
        return pedidoRepository.save(pedidoDoBanco);
    }

    @Override
    public void apagarPedido(UUID id) {
        // ID do Pedido é Long, não UUID
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido não encontrado com o ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Pedido> buscarPorId(UUID id) {
        return pedidoRepository.findById(id);
    }
}