package com.manage.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank(message = "Name is mandatory") String name, @Email(message = "Enter Valid email") String email, String address) {
}
