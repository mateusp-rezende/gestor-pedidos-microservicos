package br.com.rezende.pedido_service.service.cliente;

import br.com.rezende.pedido_service.model.Cliente;
import br.com.rezende.pedido_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteRepository repository;

    @Override
    public Cliente criarCliente(Cliente cliente) {
        return repository.save(cliente);
    };

    @Override
    public List<Cliente> retornarTudo() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    public Cliente alterarCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public void apagarCliente(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Cliente recuperarClientePorId(UUID id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public Cliente recuperarClientePorTelefone(String telefone) {
        // TODO Auto-generated method stub
        return repository.findByTelefone(telefone);
    }
}
