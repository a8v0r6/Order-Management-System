package com.manage.order.dto;

import java.util.List;

public record OrderDTO(Integer customerId, List<OrderItemDTO> items) {}