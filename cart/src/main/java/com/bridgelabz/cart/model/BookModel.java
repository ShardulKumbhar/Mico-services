package com.bridgelabz.cart.model;

import lombok.Data;

@Data
public class BookModel {
    private long Id;

    private String bookName;

    private String author;

    private String description;

    private String logo;

    private double price;

    private int quantity;

}



