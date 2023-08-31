package vendas.repositories;

import vendas.entites.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository repository;
    private static Category cat1,cat2,cat3;

    @BeforeEach
    void iniciando(){
        cat1 = new Category(1L, "Electronics");
        cat2 = new Category(2L, "Books");
        cat3 = new Category(3L, "Computers");
        repository.saveAll(Arrays.asList(cat1,cat2,cat3));
    }

    @Test
    @DirtiesContext
    public void listarCategoriaRepository(){
        List<Category> categoryList = repository.findAll();
        assertNotNull(categoryList);
        assertEquals(3,categoryList.size());
    }

    @Test
    @DirtiesContext
    void listarCategoriaRepositoryId(){
        Category categorySalva = repository.findById(cat1.getId()).get();
        assertNotNull(categorySalva);
        assertEquals(cat1.getId(), categorySalva.getId());
    }

}
