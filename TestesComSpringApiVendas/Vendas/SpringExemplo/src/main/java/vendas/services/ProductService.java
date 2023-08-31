package vendas.services;
import vendas.entites.Product;
import vendas.repositories.ProductRepository;
import vendas.services.exceptions.DatabaseExceptions;
import vendas.services.exceptions.ProdutosException;
import vendas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listarProdutos(){
        return  productRepository.findAll();
    }

    public Product listarProdutoId (Long id){
        Optional<Product> obj=productRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id + " Não encontrado!"));
    }

    public Product listarProdutoNome (String name){
        Optional<Product> obj=productRepository.findByName(name);
        return obj.orElseThrow(() -> new ProdutosException("Produto de nome: " + name + " Não encontrado!"));
    }

    public Product insert(Product obj){
        return   productRepository.save(obj);
    }

    public void  delete(Long id ){
        try {
            if (productRepository.existsById(id)){
                productRepository.deleteById(id);
            }else {
                throw new ResourceNotFoundException(id +" Não encontrado!");
            }
        }catch (DataIntegrityViolationException e){
            throw  new DatabaseExceptions(" Produto não pode ser removido! ");
        }
    }

    public Product update(Long id,Product obj){
        try {
            Product product = productRepository.getReferenceById(id);
            updateData(product,obj);
            return productRepository.save(product);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void updateData(Product product, Product obj) {
        product.setName(obj.getName());
        product.setDescription(obj.getDescription());
        product.setPrice(obj.getPrice());
        product.setImgUrl(obj.getImgUrl());
    }

}
