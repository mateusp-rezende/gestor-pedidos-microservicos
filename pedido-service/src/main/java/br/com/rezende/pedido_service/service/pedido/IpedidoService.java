package br.com.rezende.pedido_service.service.pedido;

import br.com.rezende.pedido_service.model.Pedido;
import br.com.rezende.pedido_service.model.SituacaoPedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IpedidoService {
    Pedido criarPedido(Pedido pedido);
    Pedido alterarPedido(UUID id, Pedido dadosPedido);
    Pedido alterarSituacao(UUID id, SituacaoPedido novaSituacao);
    void apagarPedido(UUID id);
    List<Pedido> buscarTodos();
    Optional<Pedido> buscarPorId(UUID id);


}