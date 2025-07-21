package br.com.rezende.pedido_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@ToString(exclude = {"senha", "vendedores"})
public class Empresa {

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

    @Column(name = "endereco")
    @JsonProperty("endereco")
    private String endereco;

    @Column(name = "telefone")
    @JsonProperty("telefone")
    private String telefone;

    @Email(message = "O formato do e-mail é inválido.")
    @NotBlank(message = "O e-mail não pode ser vazio.")
    @Column(name = "email", unique = true, nullable = false)
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "O CPF/CNPJ não pode ser vazio.")
    // Aqui o @Column é útil para garantir o nome exato da coluna no banco
    @Column(name = "cpf_ou_cnpj", unique = true, nullable = false)
    @JsonProperty("cpfOuCnpj") // E o @JsonProperty resolve o mapeamento do JSON
    private String cpfOuCnpj;

    @NotBlank(message = "A senha não pode ser vazia.")
    @Column(name = "senha", nullable = false)
    @JsonProperty(value = "senha", access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @JsonManagedReference("empresa-vendedores")
    @OneToMany(
            mappedBy = "empresa", // Indica que o lado "dono" da relação é o campo "empresa" na classe Vendedor
            cascade = CascadeType.ALL, // Garante que, ao salvar uma empresa, os vendedores novos também sejam salvos
            orphanRemoval = true // Garante que, se um vendedor for removido desta lista, ele seja apagado do banco
    )

    private List<Vendedor> vendedores;

    // ... equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
