package com.bridgelabz.cart.dto;

import com.bridgelabz.cart.model.BookModel;
import lombok.Data;

import java.util.Optional;

@Data
public class CartListDTO {

    BookModel book;

    private long bookId;

    int quantity;

    long Id;

    long userId;

    public CartListDTO() {
    }

}
