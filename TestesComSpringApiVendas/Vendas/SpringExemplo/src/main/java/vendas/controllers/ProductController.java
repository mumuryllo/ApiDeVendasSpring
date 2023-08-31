package vendas.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vendas.entites.Product;
import vendas.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Feature - Produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = " : Lista todos os produtos", method = "GET")
    public ResponseEntity<List<Product>> findAll(){
        List<Product> list= productService.listarProdutos();
        return  ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    @Operation(summary = " : Lista todos os produtos pelo ID", method = "GET")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Product obj = productService.listarProdutoId(id);
        return  ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/name/{name}")
    @Operation(summary = " : Lista todos os produtos pelo nome", method = "GET")
    public ResponseEntity<Product> findByName(@PathVariable String name){
        Product obj = productService.listarProdutoNome(name);
        return  ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/save")
    @Operation(summary = " : Insere  os dados do produtos", method = "POST")
    public ResponseEntity<Product> insert(@RequestBody Product obj){
        obj = productService.insert(obj);
        return  ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = " : Exclui um produto pelo id", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = " : Atualiza os dados do  produto pelo ID", method = "UPDATE")
    public ResponseEntity<Product> update(@PathVariable Long id,@RequestBody Product obj){
        obj= productService.update(id,obj);
        return  ResponseEntity.status(200).body(obj);
    }



}
