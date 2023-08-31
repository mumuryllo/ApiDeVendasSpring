package vendas.entites;

import vendas.entites.compostas.OrderItemPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialverionUID=1L;


    @EmbeddedId
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private OrderItemPK id = new OrderItemPK();
    private Integer quantity;
    private Double price;

    public OrderItem() {
    }



    public OrderItem(Order order,Product product,Integer quantity, Double price) {
        super();
        this.quantity = quantity;
        this.price = price;
        id.setOrder(order);
        id.setProduct(product);
    }

    public Integer getQuantity() {
        return quantity;
    }


    @JsonIgnore
    public Order getOrder(){
        return id.getOrder();
    }

    public void setOrder(Order order){
        id.setOrder(order);
    }

    @JsonIgnore
    public Product getProduct(){
        return id.getProduct();
    }

    public void setOrder(Product product){
        id.setProduct(product);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getSubTotal(){
        return quantity*price;
    }

    public OrderItemPK getId() {
        return id;
    }

    public void setId(OrderItemPK id) {
        this.id = id;
    }
}
