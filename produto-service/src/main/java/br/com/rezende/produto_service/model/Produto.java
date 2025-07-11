package br.com.rezende.produto_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@ToString
public class Produto {

    @Id
    @GeneratedValue
    @UuidGenerator // Geração de ID moderna para UUIDs
    @Column(columnDefinition = "CHAR(36)") // Garante que a coluna no DB seja de texto
    private UUID id;

    @NotBlank(message = "O nome do produto não pode ser vazio.")
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank(message = "O tipo não pode ser vazio.")
    @Column(nullable = false)
    private String tipo; // Ex: "PRODUTO", "SERVIÇO"

    @NotBlank(message = "A unidade de medida não pode ser vazia.")
    @Column(nullable = false)
    private String unidadeMedida; // Ex: "UN", "KG", "L"

    @NotNull(message = "O valor não pode ser nulo.")
    @Column(nullable = false)
    private Double valor;

    @NotNull(message = "O preço de custo não pode ser nulo.")
    @Column(nullable = false)
    private Double precoDeCusto;

    private String descricao; // Opcional, sem restrições

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}