package com.manage.order.service;

import com.manage.order.dto.OrderDTO;
import com.manage.order.dto.OrderItemDTO;
import com.manage.order.dto.OrderResponseDTO;
import com.manage.order.entity.Item;
import com.manage.order.entity.Order;
import com.manage.order.entity.Product;
import com.manage.order.entity.User;
import com.manage.order.exception.*;
import com.manage.order.repository.OrderRepository;
import com.manage.order.repository.ProductRepository;
import com.manage.order.repository.UserRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Override
    public Integer createOrder(OrderDTO dto) {
        User user = userRepository.findById(dto.customerId()).orElseThrow(() -> new UserNotFoundException("No such user exists"));
        Order order = new Order();
        order.setUser(user);

        dto.items().forEach(itemDTO -> {
            Item item = new Item();
            item.setItemName(itemDTO.itemName());
            item.setPrice(itemDTO.price());
            item.setQuantity(itemDTO.quantity());
            Product p = productRepository.findByName(item.getItemName()).orElseThrow(()-> new ProductNotFoundException("Product not found"));
            if (p.getAvailableQuantity() < item.getQuantity()) {
                log.error("Order {} could not be placed", order.getOrderId());
                throw new OutOfStockException("One or more Products are unavailable");
            }
            p.setAvailableQuantity(p.getAvailableQuantity() - item.getQuantity());
            if (itemDTO.price().compareTo(p.getPrice()) != 0) {
                log.error("Item price incorrect");
                throw new IncorrectOrderException("Incorrect order details");
            }
            order.addItem(item);
        });
        Order savedOrder = orderRepository.save(order);

        Integer orderId = savedOrder.getOrderId();
        asyncService.confirmOrder(savedOrder);

        log.info("save complete : {}", new Date());
        return orderId;
    }

    @Override
    public Page<OrderResponseDTO> getOrdersByCustomerId(Integer customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());

        Page<Order> orderPage = orderRepository.findByUserCustomerIdAndIsDeletedFalse(customerId, pageable);

        return orderPage.map(this::mapToDTO);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setDeleted(true);
        order.setStatus("Deleted");
        try {
            orderRepository.save(order);
        } catch (OptimisticLockException e) {
            log.error("Could not delete Order");
        }
    }

    @Override
    public void updateOrderStatus(Integer orderId, String status) {
        Optional<Order> o = orderRepository.findById(orderId);
        if (o.isEmpty() || o.get().isDeleted()) {
            throw new OrderNotFoundException("Order not found");
        }
        int updated = orderRepository.updateOrderStatus(status, orderId, o.get().getVersion());
        if (updated == 0) {
            throw new OptimisticLockException("Order was modified");
        }
    }

    private OrderResponseDTO mapToDTO(Order order) {
        return new OrderResponseDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getItemList().stream()
                        .map(item -> new OrderItemDTO(item.getItemName(), item.getQuantity(), item.getPrice()))
                        .toList(),
                order.getUser().getCustomerId(),
                order.getUser().getName(),
                order.getStatus(),
                order.getTotalValue());
    }

}
