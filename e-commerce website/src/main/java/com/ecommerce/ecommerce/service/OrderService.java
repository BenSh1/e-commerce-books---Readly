package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.entity.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public void addOrder(Order order) {
        orderDao.save(order);
    }


    public Order getOrder(Long id) {
        return orderDao.findOrderById(id);
    }

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
    public List<OrderDetails> convertToOrderDetails(List<CartItems> cartItems) {

        List<OrderDetails> orderDetails = cartItems.stream().map(cartItem -> {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setBook(cartItem.getBook());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getBook().getPrice());

            return orderDetail;
        }).collect(Collectors.toList());
        return orderDetails;
    }





/*
    @Transactional
    public Order checkingQuantityEachBookInCart(List<CartItems> cartItems) {

        //update the stock for each book in the cartItems
        for(CartItems cartItem : cartItems) {

            Book book = bookDao.findByIdWithLock((long)cartItem.getBook().getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getStock() < cartItem.getQuantity()) {
                if(cartItem.getQuantity() == 1){
                    cartService.removeBookFromCart(book.getBookId(),currentUser);
                }
                model.addAttribute("titleOfBook", book.getTitle());
                return "bookIsOutOfStock";
                //throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }

            book.setStock(book.getStock() - cartItem.getQuantity());
            bookService.addBook(book);
        }

    }

 */



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

/*
    @Transactional
    public Order processOrder(User currentUser, List<CartItems> cartItems) {

        try{
            //update the stock for each book in the cartItems
            for (CartItems cartItem : cartItems) {
                Book book = bookDao.findByIdWithLock((long)cartItem.getBook().getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                if (book.getStock() < cartItem.getQuantity()) {
                    throw new RuntimeException("Not enough stock for book: " + book.getTitle());
                }
                book.setStock(book.getStock() - cartItem.getQuantity());
                bookDao.save(book);
            }
        }
        catch(RuntimeException e){
            throw e;
        }

        // Process the order as usual
        List<OrderDetails> orderDetails = cartItems.stream().map(cartItem -> {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setBook(cartItem.getBook());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getBook().getPrice());
            return orderDetail;
        }).collect(Collectors.toList());

        //return orderRepository.save(new Order(orderDetails, currentUser));
        return this.createOrder(orderDetails, currentUser);

    }

 */

}
