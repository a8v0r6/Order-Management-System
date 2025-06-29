package com.manage.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private User user;
    private String status = "Pending";
    private BigDecimal totalValue;
    @CreationTimestamp
    private Date orderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();
    private boolean isDeleted = false;
    @Version
    private Integer version;

    public void addItem(Item item) {
        itemList.add(item);
        item.setOrder(this);
    }

}
