package com.bridgelabz.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bridgelabz.cart.dto.CartDTO;
import com.bridgelabz.cart.dto.CartListDTO;
import com.bridgelabz.cart.exception.BookStoreException;
import com.bridgelabz.cart.model.BookModel;
import com.bridgelabz.cart.model.CartModel;
import com.bridgelabz.cart.model.UserModel;
import com.bridgelabz.cart.repository.CartRepository;
import com.bridgelabz.cart.util.Response;
import com.bridgelabz.cart.util.TokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CartService implements ICartService
{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public Response addToCart(String token, CartDTO cartDto)
    {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        System.out.println("user" + isUserPresent);

        if(isUserPresent.Id == id)
        {
            BookModel isBookPresent = restTemplate.getForObject("http://localhost:8082/book/getBookById/" + cartDto.getBookId(), BookModel.class);
            System.out.println("book" + isBookPresent);
            if(isBookPresent != null)
            {
                CartModel cartEntity = modelMapper.map(cartDto, CartModel.class);
                cartEntity.setBookId(cartDto.getBookId());
                cartEntity.setUserId(id);
                cartEntity.setQuantity(cartDto.getQuantity());
                System.out.println("cart entity" + cartEntity);
                CartModel cart = cartRepository.save(cartEntity);
                return new Response(200, "Book with id:" + cartDto.getBookId() + " Added to cart.", cart);

            } else
            {
                throw new BookStoreException(404, "Book not found");
            }
        }
        else
        {
            throw new BookStoreException(404, "User not found");
        }
    }

    @Override
    public List<CartListDTO> getUserCart(String token)
    {
        List<CartListDTO> cartList = new ArrayList<>();
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);

        if(isUserPresent.Id == id)
        {
            System.out.println(id);
            List<CartModel> usersOrders = cartRepository.findAllByUserId(id);
            System.out.println("checked data" + usersOrders);
            usersOrders.forEach(cart -> {
                CartListDTO cartData = new CartListDTO();
                cartData.setId(cart.getId());
                cartData.setBookId(cart.getBookId());
                cartData.setQuantity(cart.getQuantity());
                cartData.setUserId(cart.getUserId());
                BookModel book = restTemplate.getForObject("http://localhost:8082/book/getBookById/" + cart.getBookId(), BookModel.class);
                System.out.println("CartBook" + book);
                cartData.setBook(book);
                System.out.println("cart data" + cartData);
                cartList.add(cartData);
            });
            return cartList;
        }
        else
        {
            throw new BookStoreException(404, "User not found.");
        }
    }
	@Override
	public Response removeCartItem(String token, long cartId)
	{
		long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
		if(isUserPresent.Id == id)
		{
			Optional<CartModel> isCartItemPresent = cartRepository.findById(cartId);
			if(isCartItemPresent.isPresent())
			{
				cartRepository.delete(isCartItemPresent.get());
				return new Response(200, "Removed from cart", null);
			}
			else
			{
				throw new  BookStoreException(404, "Item not in cart");
			}

		}
		else
		{
			throw new BookStoreException(404, "User not found");
		}
	}
	@Override
	public Response updateCartItem(String token, CartDTO cartDto)
	{
		long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
		if(isUserPresent.Id == id)
		{
            BookModel isBookPresent = restTemplate.getForObject("http://localhost:8082/book/getBookById/" + cartDto.getBookId(), BookModel.class);

            if(isBookPresent != null) {
				Optional<CartModel> isCartItemPresent = cartRepository.findByBookId(cartDto.getBookId());
				if(isCartItemPresent.isPresent())
				{
					isCartItemPresent.get().setQuantity(cartDto.getQuantity());
                    CartModel updatedCart = cartRepository.save(isCartItemPresent.get());
                    return new Response(200, "Cart updated.", updatedCart);
				}
				else
				{
					throw new BookStoreException(404, "Product not found in cart.");
				}

			}
			else
			{
				throw new BookStoreException(404, "Book not found");
			}
		}
		else
		{
			throw new BookStoreException(404, "User not found");
		}
	}

}
