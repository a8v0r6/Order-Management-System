package com.manage.order.service;

import com.manage.order.dto.OrderDTO;
import com.manage.order.dto.OrderListResponse;
import com.manage.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    public Integer createOrder(OrderDTO dto);

    Page<OrderResponseDTO> getOrdersByCustomerId(Integer customerId, int page, int size);

    public void deleteOrder(Integer orderId);

    public void updateOrderStatus(Integer orderId, String status);

    List<OrderListResponse> getAllOrders(List<Integer> custIdList);
}
