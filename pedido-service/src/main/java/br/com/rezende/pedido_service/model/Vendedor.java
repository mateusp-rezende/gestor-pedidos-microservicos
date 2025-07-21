package br.com.rezende.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "vendedores")
@Getter
@Setter
@ToString(exclude = {"empresa", "pedidos"})
public class Vendedor {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @JsonProperty("id")
    private UUID id;
    @NotBlank(message = "O nome não pode ser vazio.")
    @Column(name = "nome", unique = true, nullable = false)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "data_contratacao", nullable = false)
    private LocalDate dataContratacao;

    @Column(name = "data_demissao") // A data de demissão é opcional
    private LocalDate dataDemissao;

    // --- RELACIONAMENTO COM EMPRESA ADICIONADO ---

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    @JsonBackReference("empresa-vendedores")
    private Empresa empresa;

    // Um Vendedor pode ter Muitos Pedidos
    @JsonManagedReference("vendedor-pedidos")
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.EAGER)
    private Set<Pedido> pedidos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendedor vendedor = (Vendedor) o;
        return Objects.equals(id, vendedor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
