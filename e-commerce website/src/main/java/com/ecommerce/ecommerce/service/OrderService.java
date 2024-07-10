package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.OrderDetailsRepository;
import com.ecommerce.ecommerce.dao.OrderRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import com.ecommerce.ecommerce.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public Order createOrder(List<OrderDetails> orderDetailsList ){

        Order order = new Order();
        order.setOrderDate(new Date());
        double totalAmount = orderDetailsList.stream().mapToDouble(detail -> detail.getPrice() * detail.getQuantity()).sum();
        order.setTotalAmount(totalAmount);

        //order.setCustomer(null);
        order = orderRepository.save(order);
        //System.out.println("==========================currentUser==============: " + currentUser.getFirstName());

        for (OrderDetails detail : orderDetailsList) {
            detail.setOrder(order);
            orderDetailsRepository.save(detail);
        }
        return order;
    }

}
