package com.bridgelabz.order.controller;
import java.util.List;

import com.bridgelabz.order.dto.OrderDTO;
import com.bridgelabz.order.dto.OrderList;
import com.bridgelabz.order.model.OrderModel;
import com.bridgelabz.order.service.IOrderService;
import com.bridgelabz.order.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    IOrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Response> placeOrder(@RequestHeader String token, @RequestBody OrderDTO orderDTO){
        Response response = orderService.placeOrder(token,orderDTO);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @DeleteMapping("/cancelOrder")
    public ResponseEntity<Response> cancelOrder(@RequestHeader String token,@RequestParam("orderId") long orderId){
        Response response = orderService.cancelOrder(token,orderId);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllOrder")
    public ResponseEntity<List<?>> getAllOrders(@RequestHeader String token){

        List<OrderModel> orders = orderService.getAllOrders(token);
        return new ResponseEntity<List<?>>(orders,HttpStatus.OK);
    }

}