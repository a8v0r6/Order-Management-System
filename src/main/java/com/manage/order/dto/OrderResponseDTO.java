package com.manage.order.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record OrderResponseDTO(
    Integer orderId,
    Date orderDate,
    List<OrderItemDTO> items,
    Integer customerId,
    String customerName,
    String status,
    BigDecimal total
) {}
