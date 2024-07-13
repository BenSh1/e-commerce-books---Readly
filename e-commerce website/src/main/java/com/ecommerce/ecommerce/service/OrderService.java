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

        order = orderRepository.save(order);


        for (OrderDetails detail : orderDetailsList) {
            //order.add(detail);
            detail.setOrder(order);
            orderDetailsRepository.save(detail);
        }

        return order;
    }

}
