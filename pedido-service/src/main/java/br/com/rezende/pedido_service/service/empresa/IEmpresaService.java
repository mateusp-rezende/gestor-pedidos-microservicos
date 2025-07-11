package br.com.rezende.pedido_service.service.empresa;

import br.com.rezende.pedido_service.model.Empresa;

import java.util.List;
import java.util.UUID;

public interface IEmpresaService {

    public Empresa criarEmpresa(Empresa empresa);
    public List<Empresa> retornarTudo();
    public Empresa alterarEmpresa(Empresa empresa);
    public void ApagarEmpresa(UUID id);

}
