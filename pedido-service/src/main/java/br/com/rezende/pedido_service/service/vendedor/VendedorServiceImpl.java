package br.com.rezende.pedido_service.service.vendedor;

import br.com.rezende.pedido_service.dto.VendasVendedorDTO;
import br.com.rezende.pedido_service.model.Empresa;
import br.com.rezende.pedido_service.model.Vendedor;
import br.com.rezende.pedido_service.repository.EmpresaRepository;
import br.com.rezende.pedido_service.repository.PedidoRepository;
import br.com.rezende.pedido_service.repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VendedorServiceImpl implements IVendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public Vendedor criarVendedor(Vendedor dadosVendedor) {
        if (dadosVendedor.getEmpresa() == null || dadosVendedor.getEmpresa().getId() == null) {
            throw new IllegalArgumentException("A empresa do vendedor é obrigatória.");
        }
        Empresa empresaManaged = empresaRepository.findById(dadosVendedor.getEmpresa().getId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa com ID " + dadosVendedor.getEmpresa().getId() + " não encontrada."));

        Vendedor novoVendedor = new Vendedor();
        novoVendedor.setNome(dadosVendedor.getNome());
        novoVendedor.setEmpresa(empresaManaged);
        novoVendedor.setDataContratacao(LocalDate.now());
        novoVendedor.setDataDemissao(null);

        return vendedorRepository.save(novoVendedor);
    }

    @Override
    @Transactional
    public Vendedor alterarVendedor(UUID id, Vendedor dadosVendedor) {
        Vendedor vendedorDoBanco = vendedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));
        vendedorDoBanco.setNome(dadosVendedor.getNome());
        return vendedorRepository.save(vendedorDoBanco);
    }

    @Override
    @Transactional
    public Vendedor demitirVendedor(UUID id) {
        Vendedor vendedorDoBanco = vendedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));
        if (vendedorDoBanco.getDataDemissao() == null) {
            vendedorDoBanco.setDataDemissao(LocalDate.now());
        }
        return vendedorRepository.save(vendedorDoBanco);
    }

    @Override
    @Transactional
    public Vendedor readmitirVendedor(UUID id) {
        Vendedor vendedorDoBanco = vendedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com o ID: " + id));
        if (vendedorDoBanco.getDataDemissao() != null) {
            vendedorDoBanco.setDataDemissao(null);
        }
        return vendedorRepository.save(vendedorDoBanco);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vendedor> buscarTodos() {
        return vendedorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vendedor> buscarPorId(UUID id) {
        return vendedorRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendasVendedorDTO> calcularVendasPorVendedor() {
        // A forma mais eficiente: delega o cálculo para o banco de dados
        return pedidoRepository.findVendasPorVendedor();
    }
}
