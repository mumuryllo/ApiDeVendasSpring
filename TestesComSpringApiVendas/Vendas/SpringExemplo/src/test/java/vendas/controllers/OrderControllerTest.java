package vendas.controllers;

import vendas.entites.Order;
import vendas.entites.User;
import vendas.enums.OrderStatus;
import vendas.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.time.Instant;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static User u1,u2;
    private static Order o1,o2,o3;

    @Before
    public void iniciando()  {
        u1 = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        u2 = new User(2L, "Alex Green", "alex@gmail.com", "977777777", "123456");
        o1 = new Order(1L, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        o2 = new Order(2L, Instant.parse("2019-07-21T03:42:10Z"),OrderStatus.WAITING_PAYMENT, u2);
        o3 = new Order(3L, Instant.parse("2019-07-22T15:21:22Z"),OrderStatus.DELIVERED, u1);
    }

    @Test
    public void findAll() throws Exception{
        when(service.listarPedidos()).thenReturn(Arrays.asList(o1,o2,o3));
        mockMvc.perform(get("/order").
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(Arrays.asList(o1,o2,o3)))).
                andDo(print())
                .andExpect(jsonPath("$.length()").value(3));
        verify(service).listarPedidos();
        verify(service, times(1)).listarPedidos();
    }

    @Test
    public void findById() throws Exception{
        when(service.listarPedidoId(o2.getId())).thenReturn(o2);
        mockMvc.perform(get("/order/{id}", o2.getId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(o2))).
                 andExpect(status().isOk()).
                 andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.orderStatus").value("WAITING_PAYMENT")).
                 andExpect(jsonPath("$.client.id").value(2L)).
                 andDo(print());
    }
}