package cci.fr.TP2_java.controllers;

import cci.fr.TP2_java.entities.MenuItem;
import cci.fr.TP2_java.entities.Order;
import cci.fr.TP2_java.entities.OrderItem;
import cci.fr.TP2_java.entities.OrderStatus;
import cci.fr.TP2_java.repositories.MenuItemRepository;
import cci.fr.TP2_java.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        menuItemRepository.deleteAll();
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        MenuItem menuItem = menuItemRepository.save(new MenuItem(null, "Burger", 12.50));
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(menuItem);
        orderItem.setQuantity(2);
        Order order = new Order();
        order.getItems().add(orderItem);
        Order saved = orderRepository.save(order);

        ResponseEntity<Order> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/orders/" + saved.getOrderId(),
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(OrderStatus.WAITING, response.getBody().getStatus());
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    void getOrderById_shouldReturn404WhenNotFound() {
        System.out.println("PORT: " + port);
        try {
            restTemplate.getForEntity(
                    "http://localhost:" + port + "/api/orders/999",
                    Order.class
            );
            fail("Should have thrown exception");
        } catch (HttpClientErrorException e) {
            System.out.println("STATUS: " + e.getStatusCode());
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }
}