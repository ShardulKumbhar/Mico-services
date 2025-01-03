package com.bridgelabz.order.service;


import com.bridgelabz.order.dto.OrderDTO;
import com.bridgelabz.order.dto.OrderList;
import com.bridgelabz.order.exception.BookStoreException;
import com.bridgelabz.order.model.BookModel;
import com.bridgelabz.order.model.OrderModel;
import com.bridgelabz.order.model.UserModel;
import com.bridgelabz.order.repository.OrderRepository;
import com.bridgelabz.order.util.Response;
import com.bridgelabz.order.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class OrderService implements IOrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response placeOrder(String token, OrderDTO orderDTO)
    {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);

        if(isUserPresent.Id == id)
        {
            BookModel isBookPresent = restTemplate.getForObject
                    ("http://localhost:8082/book/getBookById/" + orderDTO.getBookId(), BookModel.class);

            if(isBookPresent != null)
            {
                OrderModel order = modelMapper.map(orderDTO, OrderModel.class);
                order.setUserId(id);
                order.setBookId(orderDTO.getBookId());
                order.setAddress(orderDTO.getAddress());
                order.setPrice(orderDTO.getPrice());
                order.setQuantity(orderDTO.getQuantity());
                OrderModel orderPlaced = orderRepository.save(order);
                isBookPresent.setQuantity(isBookPresent.getQuantity() - orderDTO.getQuantity());
                BookModel updatedQuantity = restTemplate.getForObject
                        ("http://localhost:8082/book/updateBookQuantity?bookName="+ isBookPresent.getBookName() + "&quantity=" + isBookPresent.getQuantity(), BookModel.class);
                return new Response(200, "Order placed successfully", orderPlaced);
            }
            else
            {
                throw new BookStoreException(404, "Book not found.");
            }
        }
        else
        {
            throw new BookStoreException(404, "User not found.");
        }
    }

    @Override
    public Response cancelOrder(String token, long orderId)
    {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent.Id == id)
        {
            Optional<OrderModel> doesOrderExists = orderRepository.findById(orderId);
            if(doesOrderExists.isPresent())
            {
                BookModel isBookPresent = restTemplate.getForObject
                        ("http://localhost:8082/book/getBookById/" + doesOrderExists.get().getBookId(), BookModel.class);
                if(isBookPresent != null)
                {
                    isBookPresent.setQuantity(isBookPresent.getQuantity() + doesOrderExists.get().getQuantity());
                    BookModel updatedQuantity = restTemplate.getForObject
                            ("http://localhost:8082/book/updateBookQuantity?bookName="+ isBookPresent.getBookName() + "&quantity=" + isBookPresent.getQuantity(), BookModel.class);                    orderRepository.deleteById(orderId);
                    return new Response(200, "Order canceled", null);
                }
                else
                {
                    throw new BookStoreException(404, "Book not found.");
                }
            }
            else
            {
                throw new BookStoreException(400, "No such order has been placed.");
            }
        }
        else
        {
            throw new BookStoreException(404, "User not found.");
        }
    }

    @Override
    public List<OrderModel> getAllOrders(String token)
    {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent.Id == id)
        {
            List<OrderModel> orders = orderRepository.findAll();
            return orders;
        }
        else
        {
            throw new BookStoreException(404, "User not found.");
        }
    }



}