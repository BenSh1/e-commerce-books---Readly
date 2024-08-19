package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import com.ecommerce.ecommerce.entity.User;

import java.util.List;

public interface OrderDao {

    List<OrderDetails> findAllOrderDetails(int theId);

    List<OrderDetails> findDetailsByOrderId(int theId);

    Order findOrderById(Long theId);

    void save(Order theOrder);

    List<Order> findOrdersByIdOfCustomer(Long theId);

    void deleteOrderById(int theId);

    //List<Order> findOrdersByIdOfBook(Long theId);
    //List<Order> findOrdersByUsername(String username);
    List<Order> findOrdersByUsername(Long theId);

    List<Order> findAll();


}
