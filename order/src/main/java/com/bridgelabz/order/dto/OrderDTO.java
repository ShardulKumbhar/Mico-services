package com.bridgelabz.order.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

public @Data class OrderDTO {

    @PositiveOrZero
    private int quantity;

    private double price;

    @NotBlank(message = "Address Should Not Be Blank.")
    private String address;

    private long bookId;
}