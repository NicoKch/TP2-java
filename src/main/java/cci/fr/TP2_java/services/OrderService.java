package cci.fr.TP2_java.services;

import cci.fr.TP2_java.entities.*;
import cci.fr.TP2_java.repositories.EmployeeRepository;
import cci.fr.TP2_java.repositories.MenuItemRepository;
import cci.fr.TP2_java.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final EmployeeRepository employeeRepository;

    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.employeeRepository = employeeRepository;
    }

    // US1 - Client passe commande
    public Order createOrder(Map<Long, Integer> itemsWithQuantity) {
        Order order = new Order();
        itemsWithQuantity.forEach((menuItemId, quantity) -> {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("MenuItem introuvable : " + menuItemId));
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            order.getItems().add(orderItem);
        });
        return orderRepository.save(order);
    }

    // US2 - Client consulte sa commande
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + id));
    }

    // US3 - Employé consulte les commandes en attente
    public List<Order> getWaitingOrders() {
        return orderRepository.findByStatus(OrderStatus.WAITING);
    }

    // US4 - Employé s'affecte une commande
    public Order assignOrder(Long orderId, Long employeeId) {
        Order order = getOrderById(orderId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employé introuvable : " + employeeId));
        order.setEmployee(employee);
        order.setStatus(OrderStatus.IN_PREPARATION);
        return orderRepository.save(order);
    }

    // US5 - Employé change le statut
    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}