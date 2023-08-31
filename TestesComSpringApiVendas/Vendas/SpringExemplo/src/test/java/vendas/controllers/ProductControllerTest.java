package vendas.controllers;

import vendas.entites.Product;
import vendas.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public  void iniciando() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());

        when(productService.listarProdutos()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testFindById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("TestProduct");

        when(productService.listarProdutoId(anyLong())).thenReturn(product);

        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("TestProduct"));
    }

    @Test
    public void testFindByName() throws Exception {
        Product product = new Product();
        product.setName("TestProduct");

        when(productService.listarProdutoNome(any())).thenReturn(product);

        mockMvc.perform(get("/products/name/{name}", "TestProduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("TestProduct"));
    }

    @Test
    public void testInsert() throws Exception {
        Product product = new Product();
        product.setName("NewProduct");

        when(productService.insert(any())).thenReturn(product);

        mockMvc.perform(post("/products/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("NewProduct"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/products/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(anyLong());
    }

    @Test
    public void testUpdate() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("UpdatedProduct");

        when(productService.update(anyLong(), any())).thenReturn(product);

        mockMvc.perform(put("/products/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedProduct"));
    }
}