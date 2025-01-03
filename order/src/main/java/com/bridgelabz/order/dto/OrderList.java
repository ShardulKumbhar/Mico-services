package com.bridgelabz.order.dto;

import java.time.LocalDate;

import com.bridgelabz.order.model.UserModel;
import lombok.Data;
@Data
public class OrderList {

    UserModel user;

    long id;

    private int quantity;

    private double price;

    private long bookId;

    private String address;

    private LocalDate orderDate;

    public OrderList() {

    }
}