package com.bridgelabz.cart.controller;

import com.bridgelabz.cart.dto.CartDTO;
import com.bridgelabz.cart.dto.CartListDTO;
import com.bridgelabz.cart.service.ICartService;
import com.bridgelabz.cart.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("cart")
@Slf4j
public class CartController {


    @Autowired
    ICartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<Response> addBookToCart(@RequestHeader String token,@RequestBody CartDTO cartDto)
    {

        Response response = cartService.addToCart(token,cartDto);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }


    @DeleteMapping("/removeCartItem")
	public ResponseEntity<Response> removeCartItem(@RequestHeader String token,@RequestParam("cartId") long cartId)
	{
		Response response = cartService.removeCartItem(token,cartId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}


	@PutMapping("/updateCartItem")
	public ResponseEntity<Response> updateCartItem(@RequestHeader String token,@RequestBody CartDTO cartDto)
	{
		Response response = cartService.updateCartItem(token,cartDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}


    @GetMapping("/getAllUserCart")
    public ResponseEntity<List<?>> getAllUserCart(@RequestHeader String token)
    {
        List<CartListDTO> orders = cartService.getUserCart(token);
        return new ResponseEntity<List<?>>(orders,HttpStatus.OK);
    }
}