package vendas.services;

import vendas.entites.Order;
import vendas.entites.User;
import vendas.enums.OrderStatus;
import vendas.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vendas.services.exceptions.ResourceNotFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private static User u1,u2;
    private static Order o1,o2,o3;

    @BeforeEach
    public void iniciando() {
        MockitoAnnotations.openMocks(this);
        u1 = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        u2 = new User(2L, "Alex Green", "alex@gmail.com", "977777777", "123456");
        o1 = new Order(1L, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        o2 = new Order(2L, Instant.parse("2019-07-21T03:42:10Z"),OrderStatus.WAITING_PAYMENT, u2);
        o3 = new Order(3L, Instant.parse("2019-07-22T15:21:22Z"),OrderStatus.DELIVERED, u1);
    }

    @Test
    public void testListarPedidos() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);
        orderList.add(o3);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> result = orderService.listarPedidos();

        assertEquals(3, result.size());
        assertEquals(o1.getOrderStatus(), result.get(0).getOrderStatus());
        assertEquals(o2.getOrderStatus(), result.get(1).getOrderStatus());
        assertEquals(o3.getOrderStatus(), result.get(2).getOrderStatus());

        verify(orderRepository).findAll();
    }

    @Test
    public void testListarPedidoId() {
        when(orderRepository.findById(o1.getId())).thenReturn(Optional.of(o1));

        Order result = orderService.listarPedidoId(o1.getId());

        assertNotNull(result);
        assertEquals(o1.getOrderStatus(), result.getOrderStatus());

        verify(orderRepository).findById(o1.getId());
    }

    @Test
    public void testListarPedidoIdNaoExistente() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.listarPedidoId(6L));
    }
}