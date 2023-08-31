package vendas.services;
import vendas.entites.Category;
import vendas.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vendas.services.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    private static Category cat1,cat2,cat3;

    @BeforeEach
    public void iniciando(){
        MockitoAnnotations.openMocks(this);
        cat1 = new Category(1L, "Electronics");
        cat2 = new Category(2L, "Books");
        cat3 = new Category(3L, "Computers");
    }

    @Test
    public void testListarCategorias() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(cat1);
        categoryList.add(cat2);
        categoryList.add(cat3);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> result = categoryService.listarCategorias();
        assertEquals(3, result.size());
        assertEquals(cat1.getName(), result.get(0).getName());
        assertEquals(cat2.getName(), result.get(1).getName());
        assertEquals(cat3.getName(), result.get(2).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    public void testListarCategoriaId() {
        when(categoryRepository.findById(cat1.getId())).thenReturn(Optional.of(cat1));
        Category result = categoryService.listarCategoriaId(cat1.getId());
        assertNotNull(result);
        assertEquals(cat1.getName(), result.getName());
        verify(categoryRepository).findById(cat1.getId());
    }

    @Test
    public void testListarCategoriaIdNaoExistente() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.listarCategoriaId(6L));
    }


}
