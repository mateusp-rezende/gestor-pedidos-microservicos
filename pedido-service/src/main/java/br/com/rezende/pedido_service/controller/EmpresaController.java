package br.com.rezende.pedido_service.controller;

import br.com.rezende.pedido_service.model.Empresa;
import br.com.rezende.pedido_service.service.empresa.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaServiceImpl service;

    public EmpresaController(EmpresaServiceImpl service) {
        this.service = service;
    }


    @PostMapping("/")
    public ResponseEntity<Empresa> Criar(@RequestBody Empresa empresa ){
        Empresa nova = service.criarEmpresa(empresa);
        System.out.println("Empresa criada: " + nova); // ← Aqui

        if (nova != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/")

    public ResponseEntity<List<Empresa>> RetornarTudo(){

        return  ResponseEntity.ok(service.retornarTudo());
    }



}
