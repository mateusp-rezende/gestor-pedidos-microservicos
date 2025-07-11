package br.com.rezende.pedido_service.service.pedido;

import br.com.rezende.pedido_service.model.SituacaoPedido;
import br.com.rezende.pedido_service.model.Pedido;
import br.com.rezende.pedido_service.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public Pedido criarPedido(Pedido pedido) {
        // --- Geração do Número do Pedido ---
        LocalDate data = LocalDate.now();
        long totalPedidosDeHoje = repository.countByDataEmissao(data);
        long proximoNumero = totalPedidosDeHoje + 1;

        String pedidoData = data.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String pedidoNumero = String.format("%04d", proximoNumero);

        // Corrigido para adicionar o hífen, melhorando a legibilidade
        String numeroPedidoFormatado = pedidoData + "-" + pedidoNumero;

        // --- Registrando os dados no objeto ---
        pedido.setNumeroPedido(numeroPedidoFormatado);
        pedido.setDataEmissao(data);
        pedido.setSituacao(SituacaoPedido.PENDENTE); // Define um status inicial padrão
        pedido.setId(null); // Garante que é uma criação

        // Garante a ligação bidirecional antes de salvar (importante para o cascade)
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> item.setPedido(pedido));
        }

        return repository.save(pedido);
    }

    @Override
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
    public List<Pedido> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return repository.findById(id);
    }
}