package com.manage.order.service;

import com.manage.order.entity.Order;
import com.manage.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class AsyncService {

    @Autowired
    private OrderRepository orderRepository;

    @Async
    @Transactional
    public void confirmOrder(Order order) {
        System.out.println("calculating total");
        BigDecimal total = order.getItemList().parallelStream().map(
                item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()).setScale(2, RoundingMode.HALF_UP)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalValue(total);
        orderRepository.setTotalAndConfirmStatus(total, order.getOrderId());
        log.info("total is: {}", total);
        log.info("Sending for delivery {}", order.getOrderId());
    }
}
