package cci.fr.TP2_java.controllers;

import cci.fr.TP2_java.entities.MenuItem;
import cci.fr.TP2_java.entities.Order;
import cci.fr.TP2_java.repositories.MenuItemRepository;
import cci.fr.TP2_java.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuItem>> getMenu() {
        return ResponseEntity.ok(menuItemRepository.findAll());
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