package vendas.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialverionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Registre um nome ")
    @NotEmpty(message = "nome não pode ser vazio!")
    @Schema(description = "Nome do Cliente", example = "Muryllo Soares")
    private String name;
    @NotNull(message = "Registre um email")
    @NotEmpty(message = "email não pode ser vazio!")
    @Email(message = "Digite um email válido!")
    @Schema(description = "Seu email", example = "MurylloSoares@gmail.com")
    private String email;
    @Length(min = 9,message = "telefone precisa no máximo de 9 caracteres",max = 9)
    @Schema(description = "Seu celular", example = "12345-678 padrão americano ou 978654321")
    private String phone;

    @NotNull(message = "Registre uma senha")
    @NotEmpty(message = "senha não pode ser vazio!")
    @Length(min = 6,message = "senha precisa no minimo de 6 caracteres")
    @Schema(description = "Digite sua senha",example = "123456")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Order> orders= new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }
}


