package br.com.rezende.pedido_service.service.cliente;

import br.com.rezende.pedido_service.model.Cliente;

import java.util.List;
import java.util.UUID;

public interface IClienteService {

    public Cliente criarCliente(Cliente cliente);
    public List<Cliente> retornarTudo();

    public Cliente recuperarClientePorId(UUID id);
    public Cliente recuperarClientePorTelefone(String telefone);

    public Cliente alterarCliente(Cliente cliente);
    void apagarCliente(UUID id);
}
