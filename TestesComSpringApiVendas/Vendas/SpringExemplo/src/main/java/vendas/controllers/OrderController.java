package vendas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vendas.entites.Order;
import vendas.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
@Tag(name = "Feature - Pedidos")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    @Operation(summary = " : Lista todos os pedidos", method = "GET")
    public ResponseEntity<List<Order>> findAll() {
        List<Order> list = service.listarPedidos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = " : Lista todos os pedidos pelo id", method = "GET")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.listarPedidoId(id);
        return ResponseEntity.ok().body(obj);
    }



}
