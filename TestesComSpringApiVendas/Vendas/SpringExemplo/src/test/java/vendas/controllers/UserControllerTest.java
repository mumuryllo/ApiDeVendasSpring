package vendas.controllers;

import vendas.dto.UserDTO;
import vendas.entites.User;
import vendas.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private static User u1, u2;
    private static UserDTO userDTO1, userDTO2;

    @BeforeEach
    public  void iniciando() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        u1 = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        u2 = new User(2L, "Alex Green", "alex@gmail.com", "977777777", "123456");
        userDTO1 = new UserDTO(u1);
        userDTO2 = new UserDTO(u2);
    }

    @Test
    public void testFindAll() throws Exception {
        when(userService.listarUsuarios()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(u1.getId()))
                .andExpect(jsonPath("$[0].name").value(u1.getName()))
                .andExpect(jsonPath("$[0].email").value(u1.getEmail()))
                .andExpect(jsonPath("$[1].id").value(u2.getId()))
                .andExpect(jsonPath("$[1].name").value(u2.getName()))
                .andExpect(jsonPath("$[1].email").value(u2.getEmail()));
        verify(userService).listarUsuarios();
    }

    @Test
    public void testFindById() throws Exception {
        when(userService.listarUsuarioId(u1.getId())).thenReturn(u1);

        mockMvc.perform(get("/users/{id}", u1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(u1.getName()));

        verify(userService).listarUsuarioId(u1.getId());
    }

    @Test
    public void testInsert() throws Exception {
        when(userService.insert(any(User.class))).thenReturn(u1);

        mockMvc.perform(post("/users/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(u1.getName()))
                .andExpect(jsonPath("$.email").value(u1.getEmail()))
                .andExpect(jsonPath("$.id").value(u1.getId()));

        verify(userService).insert(any(User.class));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/users/delete/{id}", u2.getId()))
                .andExpect(status().isNoContent());

        verify(userService).delete(u2.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        u1.setId(1L);
        u1.setName("Mury");
        u1.setEmail("Muryllovisk@gmail.com");
        u1.setPhone("1234567-890");

        when(userService.update(eq(u1.getId()), any(User.class))).thenReturn(u1);

        mockMvc.perform(put("/users/update/{id}", u1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(u1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(u1.getName()))
                .andExpect(jsonPath("$.email").value(u1.getEmail()))
                .andExpect(jsonPath("$.phone").value(u1.getPhone()));

        verify(userService).update(eq(u1.getId()), any(User.class));
    }
}