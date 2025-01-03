package com.bridgelabz.order.service;

import java.util.List;

import com.bridgelabz.order.dto.OrderDTO;
import com.bridgelabz.order.dto.OrderList;
import com.bridgelabz.order.model.OrderModel;
import com.bridgelabz.order.util.Response;

public interface IOrderService {


    Response placeOrder(String token, OrderDTO orderDTO);


    Response cancelOrder(String token, long orderId);


    List<OrderModel> getAllOrders(String token);



}
