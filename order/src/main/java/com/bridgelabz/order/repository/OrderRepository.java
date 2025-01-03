package com.bridgelabz.order.repository;

import com.bridgelabz.order.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderModel, Long>
{

    Optional<OrderModel> findByUserId(long id);

    @Query(value = "SELECT * FROM orders WHERE user_id=:id",nativeQuery = true)
    List<OrderModel> getAllOrdersForUser(long id);



}
