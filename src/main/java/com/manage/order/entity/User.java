package com.manage.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NonNull
    @Column(unique = true)
    @Email(message = "Enter Valid email")
    private String email;
    private String address;
    private boolean isDeleted = false;

    public User(@NonNull String name, @NonNull String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
