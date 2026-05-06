package cci.fr.TP2_java.controllers;

import cci.fr.TP2_java.entities.Order;
import cci.fr.TP2_java.entities.OrderStatus;
import cci.fr.TP2_java.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final OrderService orderService;

    public EmployeeController(OrderService orderService) {
        this.orderService = orderService;
    }

    // US3 - Consulter les commandes en attente
    @GetMapping("/orders/waiting")
    public ResponseEntity<List<Order>> getWaitingOrders() {
        return ResponseEntity.ok(orderService.getWaitingOrders());
    }

    // US4 - S'affecter une commande
    @PatchMapping("/orders/{orderId}/assign/{employeeId}")
    public ResponseEntity<Order> assignOrder(@PathVariable Long orderId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(orderService.assignOrder(orderId, employeeId));
    }

    // US5 - Changer le statut
    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long orderId, @RequestBody Map<String, String> body) {
        OrderStatus status = OrderStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }
}