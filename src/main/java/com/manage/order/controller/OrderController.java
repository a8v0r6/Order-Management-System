package com.manage.order.controller;

import com.manage.order.dto.OrderDTO;
import com.manage.order.dto.OrderResponseDTO;
import com.manage.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO dto) {
        Integer orderId = orderService.createOrder(dto);
        return ResponseEntity.ok("Order has been placed. Order id: " + orderId);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(@RequestParam Integer custId, @RequestParam(defaultValue = "0") int page,
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
        return ResponseEntity.ok().build();
    }

}
