package br.com.rezende.pedido_service.service.empresa;

import br.com.rezende.pedido_service.model.Empresa;
import br.com.rezende.pedido_service.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaServiceImpl implements IEmpresaService{

    @Autowired
    private EmpresaRepository repository;

    @Override
    public Empresa criarEmpresa(Empresa empresa) {

        return repository.save(empresa);
    }

    @Override
    public List<Empresa> retornarTudo() {

        return (List<Empresa>)repository.findAll();
    }
    @Override
    public Empresa alterarEmpresa(Empresa empresa) {

        return repository.save(empresa);
    }

    @Override
    public void ApagarEmpresa(UUID id) {

        repository.deleteById(id);
    }

}