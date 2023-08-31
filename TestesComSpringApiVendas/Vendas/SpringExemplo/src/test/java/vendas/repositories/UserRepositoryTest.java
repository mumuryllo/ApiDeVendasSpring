package vendas.repositories;

import vendas.entites.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    private static User u1,u2;

    @BeforeEach
    public  void iniciando(){
        u1 = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        u2 = new User(2L, "Alex Green", "alex@gmail.com", "977777777", "123456");
        repository.save(u1);
        repository.save(u2);
    }

    @Test
    @DirtiesContext
    void salvarUsuarioRepository(){
        User userSalvo = repository.save(u1);
        assertNotNull(userSalvo);
        assertTrue(userSalvo.getId() > 0);
    }

    @Test
    @DirtiesContext
    void listarUsuarioRepository(){
        List<User> userList = repository.findAll();
        assertNotNull(userList);
        assertEquals(2,userList.size());
    }

    @Test
    @DirtiesContext
    void listarUsuarioRepositoryId(){
        User userSalvo = repository.findById(u1.getId()).get();
        assertNotNull(userSalvo);
        assertEquals(u1.getId(), userSalvo.getId());
    }


    @Test
    @DirtiesContext
    void atualizarUsuarioRepository(){

        User userSalvo = repository.findById(u1.getId()).get();
        userSalvo.setName("Mu");
        userSalvo.setEmail("Mumovisk@hotmail.com");

        User userAtualizado = repository.save(userSalvo);

        assertNotNull(userAtualizado);
        assertEquals("Mu", userAtualizado.getName());
        assertEquals("Mumovisk@hotmail.com",userAtualizado.getEmail());
    }

    @Test
    @DirtiesContext
    void deletarUsuarioRepository(){

        repository.save(u1);

        repository.deleteById(u1.getId());

        assertFalse(repository.existsById(u1.getId()));
    }

}