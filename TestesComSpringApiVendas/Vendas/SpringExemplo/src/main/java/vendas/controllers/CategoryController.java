package vendas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vendas.entites.Category;
import vendas.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Feature - Categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = " : Lista todas as categorias", method = "GET")
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list= categoryService.listarCategorias();
        return  ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    @Operation(summary = " : Lista todas as categorias pelo id", method = "GET")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Category obj = categoryService.listarCategoriaId(id);
        return  ResponseEntity.ok().body(obj);
    }

}
