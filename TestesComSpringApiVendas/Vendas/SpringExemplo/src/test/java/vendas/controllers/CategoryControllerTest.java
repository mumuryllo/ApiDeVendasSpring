package vendas.controllers;

import vendas.entites.Category;
import vendas.services.CategoryService;
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

import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    private CategoryService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static Category cat1,cat2,cat3;

    @Before
    public void iniciando() {
        cat1 = new Category(1L, "Electronics");
        cat2 = new Category(2L, "Books");
        cat3 = new Category(3l, "Computers");
    }

    @Test
    public void findAll() throws Exception{
        when(service.listarCategorias()).thenReturn(Arrays.asList(cat1,cat2,cat3));
        mockMvc.perform(get("/categories").
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(Arrays.asList(cat1,cat2,cat3)))).
                 andDo(print())
                .andExpect(jsonPath("$.length()").value(3));

        verify(service).listarCategorias();
        verify(service, times(1)).listarCategorias();
    }

    @Test
    public void findById() throws Exception{
        when(service.listarCategoriaId(cat2.getId())).thenReturn(cat2);
        mockMvc.perform(get("/categories/{id}", cat2.getId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(mapper.writeValueAsString(cat2))).
                 andExpect(status().isOk()).
                 andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Books")).
                 andDo(print());
    }
}