package com.manage.order.service;

import com.manage.order.entity.Item;
import com.manage.order.entity.Order;
import com.manage.order.repository.OrderRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AsyncService {

    @Autowired
    private OrderRepository orderRepository;

    @Async
    public void calculateTotal(Order order) {
        System.out.println("calculating");
        Double total = order.getItemList().parallelStream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        order.setTotalValue(total);
        order.setStatus("Confirmed");
        try{
            orderRepository.save(order);
            Thread.sleep(10000);
        }
        catch (OptimisticLockException e) {
            log.error("Exception while confirming");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("total is: {}", total);
    }
}
