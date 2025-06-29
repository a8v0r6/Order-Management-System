package com.manage.order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record OrderItemDTO(String itemName, @Min(value = 1, message = "quantity should be greater than 0") Integer quantity, @DecimalMin(value = "0.01") BigDecimal price) {}