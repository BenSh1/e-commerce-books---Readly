package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.OrderDetails;

import java.util.List;

public interface OrderDao {

    List<OrderDetails> findAllOrderDetails(int theId);

    List<OrderDetails> findDetailsByOrderId(int theId);

    }
