package com.manage.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(unique = true)
    private String name;
    @DecimalMin(value = "0.01")
    @Column(name = "price", scale = 2)
    private BigDecimal price;

    private Integer availableQuantity;

}
