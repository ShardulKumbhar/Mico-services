package com.bridgelabz.cart.service;

import com.bridgelabz.cart.dto.CartDTO;
import com.bridgelabz.cart.dto.CartListDTO;
import com.bridgelabz.cart.util.Response;

import java.util.List;

public interface ICartService {


    Response addToCart(String token, CartDTO cartDto);


    Response removeCartItem(String token, long cartId);


    Response updateCartItem(String token, CartDTO cartDto);


    List<CartListDTO> getUserCart(String token);
}