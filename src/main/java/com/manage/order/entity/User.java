package com.manage.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @NonNull
    private String name;
    @NonNull
    @Column(unique = true)
    private String email;
    private String address;
    private boolean isDeleted = false;

    public User(@NonNull String name, @NonNull String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
