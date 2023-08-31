package vendas.services;
import vendas.entites.Product;
import vendas.repositories.ProductRepository;
import vendas.services.exceptions.ProdutosException;
import vendas.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void iniciando() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarProdutos() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.listarProdutos();

        assertEquals(1, result.size());
    }

    @Test
    public void testListarProdutoIdExistente() {
        Product product = new Product();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.listarProdutoId(1L);

        assertNotNull(result);
    }

    @Test
    public void testListarProdutoIdNaoExistente() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.listarProdutoId(1L));
    }

    @Test
    public void testListarProdutoNomeExistente() {
        Product product = new Product();
        when(productRepository.findByName(any())).thenReturn(Optional.of(product));

        Product result = productService.listarProdutoNome("ProductNome");

        assertNotNull(result);
    }

    @Test
    public void testListarProdutoNomeNaoExistente() {
        when(productRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(ProdutosException.class, () -> productService.listarProdutoNome("ProductNome"));
    }

    @Test
    public void testInsert() {
        Product product = new Product();
        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.insert(new Product());

        assertNotNull(result);
    }

    @Test
    public void testDeleteExistente() {
        when(productRepository.existsById(anyLong())).thenReturn(true);

        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteNaoExistente() {
        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.delete(1L));
    }

    @Test
    public void testUpdateExistente() {
        Product existingProduct = new Product();
        when(productRepository.getReferenceById(anyLong())).thenReturn(existingProduct);
        when(productRepository.save(any())).thenReturn(existingProduct);

        Product updatedProduct = productService.update(1L, new Product());

        assertNotNull(updatedProduct);
    }

    @Test
    public void testUpdateNaoExistente() {
        when(productRepository.getReferenceById(anyLong())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> productService.update(1L, new Product()));
    }

    @Test
    public void testUpdateData() {
        Product existe = new Product();
        Product newProduct= new Product();
        newProduct.setName("NewName");
        newProduct.setDescription("NewDescription");
        newProduct.setPrice(20.0);
        newProduct.setImgUrl("newImageUrl");

        productService.updateData(existe, newProduct);

        assertEquals("NewName", existe.getName());
        assertEquals("NewDescription", existe.getDescription());
        assertEquals(20.0, existe.getPrice());
        assertEquals("newImageUrl", existe.getImgUrl());
    }
}

