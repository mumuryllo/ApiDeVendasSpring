package vendas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vendas.dto.UserDTO;
import vendas.entites.User;
import vendas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "Feature - Clientes")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = " : Lista todos os clientes", method = "GET")
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list= userService.listarUsuarios();
        List<UserDTO> listDto = list.stream().map(UserDTO::new).collect(Collectors.toList());
        return  ResponseEntity.ok().body(listDto);
    }
    @GetMapping(value = "/{id}")
    @Operation(summary = " : Lista todos os clientes pelo ID", method = "GET")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        User obj = userService.listarUsuarioId(id);
        return  ResponseEntity.ok().body(new UserDTO(obj));
    }

    @PostMapping(value = "/save")
    @Operation(summary = " : Insere os dados dos clientes", method = "POST")
    public ResponseEntity<UserDTO> insert(@RequestBody User obj){
        obj = userService.insert(obj);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(obj));
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = " : Exclui um cliente pelo id", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = " : Atualiza os dados do  cliente pelo ID", method = "UPDATE")
    public ResponseEntity<User> update(@PathVariable Long id,@RequestBody User obj){
        obj= userService.update(id,obj);
        return  ResponseEntity.status(200).body(obj);
    }

}
