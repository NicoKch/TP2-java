package cci.fr.TP2_java.controllers;

import cci.fr.TP2_java.entities.Order;
import cci.fr.TP2_java.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // US1 - Passer commande
    // Body : { "1": 2, "3": 1 } → menuItemId: quantity
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<Long, Integer> itemsWithQuantity) {
        return ResponseEntity.ok(orderService.createOrder(itemsWithQuantity));
    }

    // US2 - Consulter sa commande
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}