package br.com.rezende.pedido_service.controller;

import br.com.rezende.pedido_service.model.Cliente;
import br.com.rezende.pedido_service.service.cliente.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("clientes/")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl service;

    @PostMapping // Adicionada anotação que estava faltando
    public ResponseEntity<Cliente> criar(@RequestBody Cliente novo){
        try{
            Cliente resultado = service.criarCliente(novo);
            if(resultado != null) {
                return ResponseEntity.status(201).body(resultado);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception ex) {
            System.out.println("LOG - Erro ao cadastrar -" +ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@RequestBody Cliente cliente, @PathVariable UUID id){
        try
        {
            if (cliente.getId() == null) {
                cliente.setId(id);
            }
            Cliente resultado = service.alterarCliente(cliente);
            if (resultado != null) {
                return ResponseEntity.ok(resultado);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (Exception ex) {
            System.out.println("LOG - Erro ao atualizar -" + ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<Cliente>> retornarTudo(){
        return  ResponseEntity.ok(service.retornarTudo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> recuperarClientePorId(@PathVariable UUID id){
        Cliente resultado = service.recuperarClientePorId(id);
        if(resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/telefone")
    public ResponseEntity<Cliente> getByTelefone(@RequestParam(name="valor") String telefone) {
        Cliente resultado = service.recuperarClientePorTelefone(telefone);
        if (resultado != null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        try {
            service.apagarCliente(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception ex) {
            System.out.println("LOG - Erro ao deletar -" + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}