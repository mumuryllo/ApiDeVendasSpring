package vendas.services;

import vendas.entites.Category;
import vendas.repositories.CategoryRepository;
import vendas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listarCategorias(){
        return  categoryRepository.findAll();
    }

    public Category listarCategoriaId (Long id){
        Optional<Category> obj=categoryRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id + " Não encontrado!"));
    }

}
