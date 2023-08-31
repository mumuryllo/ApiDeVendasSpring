package vendas.services;

import vendas.entites.User;
import vendas.repositories.UserRepository;
import vendas.services.exceptions.DatabaseExceptions;
import vendas.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User u1, u2;

    @BeforeEach
    public  void iniciando() {
        MockitoAnnotations.openMocks(this);
        u1 = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        u2 = new User(2L, "Alex Green", "alex@gmail.com", "977777777", "123456");
    }

    @Test
    public void testListarUsuarios() {
        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.listarUsuarios();

        assertEquals(2, result.size());
        assertEquals(u1.getName(), result.get(0).getName());
        assertEquals(u1.getEmail(), result.get(0).getEmail());
        assertEquals(u1.getPassword(), result.get(0).getPassword());
        assertEquals(u1.getPhone(), result.get(0).getPhone());
        assertEquals(u2.getName(), result.get(1).getName());
        assertEquals(u2.getEmail(), result.get(1).getEmail());
        assertEquals(u2.getPassword(), result.get(1).getPassword());
        assertEquals(u2.getPhone(), result.get(1).getPhone());

        verify(userRepository).findAll();
    }

    @Test
    public void testListarUsuarioId() {
        when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));

        User result = userService.listarUsuarioId(u1.getId());

        assertNotNull(result);
        assertEquals(u1.getName(), result.getName());
        assertEquals(u1.getEmail(), result.getEmail());
        assertEquals(u1.getPassword(), result.getPassword());
        assertEquals(u1.getPhone(), result.getPhone());

        verify(userRepository).findById(u1.getId());
    }

    @Test
    public void testListarUsuarioId_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.listarUsuarioId(100L));

        verify(userRepository).findById(100L);
    }

    @Test
    public void testInsert() {
        when(userRepository.save(any(User.class))).thenReturn(u1);

        User result = userService.insert(u1);

        assertNotNull(result);
        assertEquals(u1.getName(), result.getName());
        assertEquals(u1.getEmail(), result.getEmail());
        assertEquals(u1.getPassword(), result.getPassword());
        assertEquals(u1.getPhone(), result.getPhone());

        verify(userRepository).save(u1);
    }

    @Test
    public void testDelete() {
        when(userRepository.existsById(u1.getId())).thenReturn(true);

        userService.delete(u1.getId());

        verify(userRepository).deleteById(u1.getId());
    }

    @Test
    public void testDelete_NotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(100L));

        verify(userRepository).existsById(100L);
    }

    @Test
    public void testDelete_DataIntegrityViolationException() {
        when(userRepository.existsById(u1.getId())).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(u1.getId());

        assertThrows(DatabaseExceptions.class, () -> userService.delete(u1.getId()));

        verify(userRepository).deleteById(u1.getId());
    }

    @Test
    public void testUpdate() {
        when(userRepository.getReferenceById(u1.getId())).thenReturn(u1);
        when(userRepository.save(any(User.class))).thenReturn(u1);

        u1.setName("Mury");
        u1.setEmail("mumu@gmail");
        u1.setPassword("1234");
        u1.setPhone("123456");
        User result = userService.update(u1.getId(), u1);

        assertNotNull(result);
        assertEquals("Mury", result.getName());
        assertEquals("mumu@gmail", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals("123456", result.getPhone());


        verify(userRepository).getReferenceById(u1.getId());
        verify(userRepository).save(u1);
    }

    @Test
    public void testUpdate_EntityNotFoundException() {
        when(userRepository.getReferenceById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userService.update(100L, u1));

        verify(userRepository).getReferenceById(100L);
    }
}
