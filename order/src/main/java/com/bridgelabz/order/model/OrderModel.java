package com.bridgelabz.order.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "orders")
public @Data class OrderModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "orderDate")
    private LocalDate orderDate = LocalDate.now();

    private int quantity;

    private double price;

    private String address;

    @Column(name = "user_id")
    private long userId;

    private long bookId;
}
