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

    }
