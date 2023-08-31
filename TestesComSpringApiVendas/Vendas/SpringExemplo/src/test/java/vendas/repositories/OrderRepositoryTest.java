package vendas.repositories;

import vendas.entites.Order;
import vendas.entites.User;
import vendas.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository repository;

    private User u1, u2;
    private Order o1, o2, o3;

    @BeforeEach
    public void iniciando() {
            u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
            u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");
            o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
            o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
            o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.DELIVERED, u1);

            entityManager.persistAndFlush(u1);
            entityManager.persistAndFlush(u2);
            entityManager.persistAndFlush(o1);
            entityManager.persistAndFlush(o2);
            entityManager.persistAndFlush(o3);
        }

    @Test
    @DirtiesContext
    void listarPedidosRepository(){
        List<Order> orderList = repository.findAll();
        assertNotNull(orderList);
        assertEquals(3,orderList.size());
    }

    @Test
    @DirtiesContext
    void listarPedidoRepositoryId(){
        Order orderSalvo = repository.findById(o1.getId()).orElse(null);
        assertNotNull(orderSalvo);
        assertEquals(o1.getId(), orderSalvo.getId());
    }
}