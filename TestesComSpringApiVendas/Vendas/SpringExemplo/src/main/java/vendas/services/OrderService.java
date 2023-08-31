package vendas.services;

import vendas.entites.Order;
import vendas.repositories.OrderRepository;
import vendas.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listarPedidos(){
        return  orderRepository.findAll();
    }

    public Order listarPedidoId (Long id){
        Optional<Order> obj=orderRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id + " Não encontrado!"));
    }

}
