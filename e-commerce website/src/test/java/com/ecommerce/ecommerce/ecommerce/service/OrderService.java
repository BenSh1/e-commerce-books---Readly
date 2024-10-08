package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.CartItemsRepository;
import com.ecommerce.ecommerce.dao.OrderDetailsRepository;
import com.ecommerce.ecommerce.dao.OrderRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import com.ecommerce.ecommerce.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Transactional
    public List<Order> getAllMyOrdersWithDetails(User user) {

        //the error is in here i must to exchange it to addDao and write jdbc query
        List<Order> orders = orderRepository.findAll();
        // Ensure orderDetails are initialized

        List<Order> tempOrders = new ArrayList<Order>();

        for (Order order : orders) {
            if(order.getUser().getId().equals(user.getId()))
            {
                tempOrders.add(order);
                order.getOrderDetails().size(); // Trigger lazy loading
            }
        }
        return tempOrders;
    }


    @Transactional
    public List<Order> getAllOrdersWithDetails() {
        List<Order> orders = orderRepository.findAll();
        // Ensure orderDetails are initialized
        for (Order order : orders) {
            order.getOrderDetails().size(); // Trigger lazy loading
        }
        return orders;
    }


    @Transactional
    public Order createOrder(List<OrderDetails> orderDetailsList , User user){

        User currentUser = userDao.findById(user.getId());

        Order order = new Order();
        order.setOrderDate(new Date());
        double totalAmount = orderDetailsList.stream().mapToDouble(detail -> detail.getPrice() * detail.getQuantity()).sum();
        order.setTotalAmount(totalAmount);
        order.setUser(currentUser);
        order.setStatus("Pending");

        order = orderRepository.save(order);


        for (OrderDetails detail : orderDetailsList) {
            //order.add(detail);
            detail.setOrder(order);
            orderDetailsRepository.save(detail);
        }

        return order;
    }

}
