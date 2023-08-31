package vendas.repositories;
import vendas.entites.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;
    private static Product p1,p2,p3,p4,p5;


    @BeforeEach
    public  void iniciando(){
         p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5,
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fm.media-amazon");
         p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0,
                "https://www.google.com/aclk?sa=l&ai");
         p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0,
                "https://www.google.com/imgres?imgurl");
         p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0,
                "https://www.google.com/");
         p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99,
                "imgres?imgurl");
        repository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));
    }


    @Test
    @DirtiesContext
    void salvarProdutoRepository(){
        Product productSalvo = repository.save(p1);
        assertNotNull(productSalvo);
        assertTrue(productSalvo.getId() > 0);
    }
    @Test
    @DirtiesContext
    void listarProdutosRepository(){
        List<Product> productList = repository.findAll();
        assertNotNull(productList);
        assertEquals(5,productList.size());
    }

    @Test
    @DirtiesContext
    void listarProdutoRepositoryId(){
        Product productSalvo = repository.findById(p1.getId()).get();
        assertNotNull(productSalvo);
        assertEquals(p1.getId(), productSalvo.getId());
    }

    @Test
    @DirtiesContext
    void listarProdutoRepositoryNome(){
        Product productSalvo = repository.findByName(p1.getName()).get();
        assertNotNull(productSalvo);
        assertEquals(p1.getName(), productSalvo.getName());
    }

    @Test
    @DirtiesContext
    void atualizarProdutoRepository(){
        Product productSalvo = repository.findById(p2.getId()).get();
        productSalvo.setName("asdfre");
        productSalvo.setDescription("qualquer coisa");
        Product productAtualizado = repository.save(productSalvo);
        assertNotNull(productAtualizado);
        assertEquals("asdfre", productAtualizado.getName());
        assertEquals("qualquer coisa",productAtualizado.getDescription());
    }

    @Test
    @DirtiesContext
    void deletarUsuarioRepository(){
        repository.save(p5);
        repository.deleteById(p5.getId());
        assertFalse(repository.existsById(p5.getId()));
    }

}
