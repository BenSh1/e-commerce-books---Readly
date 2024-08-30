package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import java.util.List;

public interface OrderDao {

    List<OrderDetails> findAllOrderDetails(int theId);
    List<OrderDetails> findDetailsByOrderId(int theId);
    Order findOrderById(Long theId);
    List<Order> findOrdersByUsername(Long theId);
    List<Order> findAll();
    List<Order> findOrdersByIdOfCustomer(Long theId);

    void save(Order theOrder);

    void deleteOrderById(int theId);



}
