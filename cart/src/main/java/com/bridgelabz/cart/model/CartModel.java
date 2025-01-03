package com.bridgelabz.cart.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart")
public @Data class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private long userId;

    private long bookId;

    private int quantity;
}