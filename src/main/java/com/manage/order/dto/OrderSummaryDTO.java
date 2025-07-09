package com.manage.order.dto;

import java.math.BigDecimal;
import java.util.Date;

public record OrderSummaryDTO(Integer orderId, Date orderDate, BigDecimal totalValue, String status) {
}
