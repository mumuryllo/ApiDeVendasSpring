package vendas.services;

import vendas.entites.User;
import vendas.repositories.UserRepository;
import vendas.services.exceptions.DatabaseExceptions;
import vendas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarUsuarios(){
        return  userRepository.findAll();
    }

    public User listarUsuarioId (Long id){
        Optional<User> obj=userRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id + " Não encontrado!"));
    }

    public User insert(User obj){
      return   userRepository.save(obj);
    }

    public void  delete(Long id ){
        try {
            if (userRepository.existsById(id)){
                userRepository.deleteById(id);
            }else {
                throw new ResourceNotFoundException(id +" Não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
           throw  new DatabaseExceptions(" Usuário não pode ser removido! ");
        }
    }

    public User update(Long id,User obj){
        try {
            User user = userRepository.getReferenceById(id);
            updateData(user,obj);
            return userRepository.save(user);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User user, User obj) {
        user.setName(obj.getName());
        user.setEmail(obj.getEmail());
        user.setPhone(obj.getPhone());
    }

}
