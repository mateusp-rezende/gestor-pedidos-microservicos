package br.com.rezende.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
public class Cliente {

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

    @NotBlank(message = "O telefone não pode ser vazio.")
    @Column(name = "telefone", nullable = false)
    @JsonProperty("telefone")
    private String telefone;

    @Email(message = "Formato de e-mail inválido.")
    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "O endereço não pode ser vazio.")
    @Column(name = "endereco", nullable = false)
    @JsonProperty("endereco")
    private String endereco;

    @NotBlank(message = "O CPF ou CNPJ não pode ser vazio.")
    @Column(name = "cpf_ou_cnpj", unique = true, nullable = false)
    @JsonProperty("cpfOuCnpj")
    private String cpfOuCnpj;

    public Cliente() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
