package com.manage.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    private Integer quantity;
    private Double price;
    private String itemName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    private Product product;

    public Item(){}

    public Item(Integer quantity, Double price, String itemName) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
    }
}
