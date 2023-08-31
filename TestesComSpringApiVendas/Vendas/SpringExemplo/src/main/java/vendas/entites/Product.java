package vendas.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialverionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Registre um nome de um produto ")
    @NotEmpty(message = "nome não pode ser vazio!")
    @Schema(description = "Nome do Produto", example = "Iphone")
    private String name;
    @NotNull(message = "Registre uma descrição de um produto  ")
    @NotEmpty(message = "descrição não pode ser vazia!")
    @Schema(description = "Descrição do Produto", example = "Essa iphone oferece praticidade no dia a dia...")
    private String description;
    @NotNull(message = "Registre o preço de um produto! ")
    @PositiveOrZero(message = "Somente numeros nulos ou positivos!")
    @Schema(description = "Preço do Produto", example = "250.50")
    private Double price;
    @NotNull(message = "Registre uma url ")
    @NotEmpty(message = "url não pode ser vazia!")
    @Schema(description = "URL da imagem  do Produto", example = "https://www.imgurl.com")
    private String imgUrl;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();


    @OneToMany(mappedBy = "id.product", fetch = FetchType.EAGER)
    private Set<OrderItem> items=new HashSet<>();

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    @JsonIgnore
    public Set<Order> getOrders(){
       Set<Order> set = new HashSet<>();
        for (OrderItem x:items) {
            set.add(x.getOrder());
        }
        return set;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Double getPrice() {
        return price;
    }


    public Set<Category> getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
