package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dao.OrderDao;
import com.ecommerce.ecommerce.dao.OrderDetailsRepository;
import com.ecommerce.ecommerce.dao.OrderRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private UserDao userDao;


    @Autowired
    private OrderDao orderDao;


    @GetMapping("/orderList")
    public String getOrderList(Model model) {
        List<Order> orders = orderService.getAllOrdersWithDetails();
        model.addAttribute("orders", orders);
        return "orderList";
    }







        @GetMapping("/orderList2")
    public String getOrderList(HttpSession session, Model model) {

        //User currentUser = (User) session.getAttribute("user");
        //if (currentUser == null) {
        //    throw new RuntimeException("User not logged in");
        //}



        // Assume getOrderForUser returns an Order object for the current user
        //Order order = orderService.getOrderForUser(currentUser);

        List<Order> orders = orderRepository.findAll();
        //System.out.println("====================================orders.size()===================================== : " + orders.size());

/*
        for (Order order : orders) {
            for(OrderDetails orderDetail : orderDetails) {
                if(order.getOrderId() == orderDetail.getOrder().getOrderId())
                {
                    order.add(orderDetail);
                }
            }
        }

 */
        model.addAttribute("orders", orders);

       // System.out.println("orders.getFirst().getOrderDetails : " + orders.getFirst().getOrderDetails());


        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();
        System.out.println("=======================================================");
        System.out.println("==========orderDetails.getFirst().getBook().getTitle()================================="+ orderDetails.getFirst().getBook().getTitle());



        /*
        for (Order order : orders) {
            for (OrderDetails orderDetail : orderDetails) {
                if(orderDetail.getOrder().getOrderId() == order.getOrderId()){
                    order.add(orderDetail);
                }
            }
        }

         */


        System.out.println("=======================================================");
        model.addAttribute("orderDetails", orderDetails);

        return "orderList";
    }



/*
    @GetMapping("/orderList")
    public String getOrderList(Model model) {

        List<Order> orders = orderRepository.findAll();

        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();


        model.addAttribute("orders", orders);
        model.addAttribute("orderDetails", orderDetails);


        return "orderList";

    }

 */





    @PostMapping("/addOrder")
    public String addOrder(Model model, HttpSession session ,
                           @AuthenticationPrincipal Authentication authentication) {

        User currentUser = (User) session.getAttribute("user");
        //User user = userService.get
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("currentUser.getFirstName()  = " + currentUser.getFirstName());


        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        List<OrderDetails> orderDetails = cartItems.stream().map(cartItem -> {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setBook(cartItem.getBook());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getBook().getPrice());

            return orderDetail;
        }).collect(Collectors.toList());



        Order order = orderService.createOrder(orderDetails, currentUser);
        model.addAttribute("order", order);

        cartService.clearCart(currentUser);

        return "orderConfirmation";
    }




}
