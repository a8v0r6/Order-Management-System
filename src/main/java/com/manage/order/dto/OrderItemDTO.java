package com.manage.order.dto;

import jakarta.validation.constraints.Min;

public record OrderItemDTO(String itemName, @Min(value = 1, message = "quantity should be greater than 0") Integer quantity, Double price) {}