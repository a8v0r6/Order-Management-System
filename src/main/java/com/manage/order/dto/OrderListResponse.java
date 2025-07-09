package com.manage.order.dto;

import java.util.List;

public record OrderListResponse(Integer customerId, List<OrderSummaryDTO> list) {
}
