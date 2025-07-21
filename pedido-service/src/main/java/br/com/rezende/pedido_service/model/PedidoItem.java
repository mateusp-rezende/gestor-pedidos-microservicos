package br.com.rezende.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator; // Importe o UuidGenerator

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pedido_itens")
@Getter
@Setter
@ToString(exclude = {"pedido"})
public class PedidoItem {

    // --- CORREÇÃO: Padronizando a geração do ID ---
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @JsonBackReference("pedido-itens")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private UUID produtoId;

    private Integer quantidade;
    private Double valorUnitario;

    @Transient
    public Double getValorTotal() {
        if (valorUnitario != null && quantidade != null) {
            return valorUnitario * quantidade;
        }
        return 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItem that = (PedidoItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
