package com.manage.order.controller;

import com.manage.order.dto.OrderDTO;
import com.manage.order.dto.OrderResponseDTO;
import com.manage.order.entity.Order;
import com.manage.order.service.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO dto) {
        Integer orderId = orderService.createOrder(dto);
        return ResponseEntity.ok("Order has been placed. Order id: " + orderId);
    }

    @GetMapping("/{custId}")
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(@PathVariable Integer custId, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(custId, page, size));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.status(201).build();
    }

}
