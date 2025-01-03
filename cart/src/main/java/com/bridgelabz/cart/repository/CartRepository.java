package com.bridgelabz.cart.repository;



import com.bridgelabz.cart.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartModel, Long>{

    Optional<CartModel> findByBookId(long bookId);

    Optional<CartModel> findByUserId(long id);

    List<CartModel> findAllByUserId(long Id);

}