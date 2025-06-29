package com.manage.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDTO(@NotNull Integer customerId, List<@Valid OrderItemDTO> items) {}