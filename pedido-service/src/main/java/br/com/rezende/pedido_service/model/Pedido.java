package br.com.rezende.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set; // <-- CORREÇÃO: Usar Set para evitar problemas de fetch
import java.util.UUID;

@Entity
@Table(name = "pedidos") // Boa prática para nomear a tabela
@Getter
@Setter
@ToString(exclude = {"cliente", "itens", "vendedor"})
public class Pedido {

    @Id
    @GeneratedValue // <-- CORREÇÃO: Padronizando a geração de ID
    @UuidGenerator
    private UUID id;

    private String numeroPedido;
    private LocalDate dataEmissao;
    private LocalDate dataEntrega;

    @Enumerated(EnumType.STRING)
    private SituacaoPedido situacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false) // Garante que o cliente é obrigatório
    private Cliente cliente;

    // Muitos Pedidos podem pertencer a Um Vendedor (opcional)
    @JsonBackReference("vendedor-pedidos")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendedor_id") // nullable=false removido para ser opcional
    private Vendedor vendedor;

    // Um Pedido pode ter Muitos Itens
    @JsonManagedReference("pedido-itens") // <-- CORREÇÃO: Nome adicionado para corresponder ao PedidoItem
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PedidoItem> itens; // <-- CORREÇÃO: Usar Set em vez de List


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
