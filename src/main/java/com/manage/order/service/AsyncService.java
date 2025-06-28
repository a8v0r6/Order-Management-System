package com.manage.order.service;

import com.manage.order.entity.Item;
import com.manage.order.entity.Order;
import com.manage.order.entity.Product;
import com.manage.order.exception.OutOfStockException;
import com.manage.order.exception.ProductNotFoundException;
import com.manage.order.repository.OrderRepository;
import com.manage.order.repository.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Async
    @Transactional
    public void confirmOrder(Order order) {

        for (Item item : order.getItemList()) {
            Product p = productRepository.findByName(item.getItemName()).orElseThrow(()-> new ProductNotFoundException("Product not found"));
            if (p.getAvailableQuantity() < item.getQuantity()) {
                throw new OutOfStockException("One or more Products are unavailable");
            }
            p.setAvailableQuantity(p.getAvailableQuantity() - item.getQuantity());
        }

        System.out.println("calculating total");
        Double total = order.getItemList().parallelStream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        order.setTotalValue(total);
        order.setStatus("Confirmed");
        Order savedOrder = new Order();
        try{
            savedOrder = orderRepository.save(order);
            Thread.sleep(10000);
        }
        catch (OptimisticLockException e) {
            log.error("Exception while confirming {}", order.getOrderId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("total is: {}", total);
        log.info("Sending for delivery {}", savedOrder.getOrderId());
    }
}
