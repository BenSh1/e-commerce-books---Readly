package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    // ConcurrentHashMap to hold locks for each book
    private final ConcurrentHashMap<Long, Lock> bookLocks = new ConcurrentHashMap<>();


    private final StockService stockService;

    public OrderService(StockService stockService,
                        OrderDao orderDao,
                        CartService cartService) {
        this.stockService = stockService;
        this.orderDao     = orderDao;
        this.cartService  = cartService;
    }


    /**
     * This function adds a new order to the database.
     * It saves the provided order object using the `orderDao`.
     *
     * @param order the order object to be added to the database.
     */
    public void addOrder(Order order) {
        orderDao.save(order);
    }

    /**
     * This function retrieves an order from the database based on its unique ID.
     * It returns the order with the specified ID.
     *
     * @param id the ID of the order to retrieve.
     * @return the order with the specified ID, or null if not found.
     */
    public Order getOrder(Long id) {
        return orderDao.findOrderById(id);
    }


    /**
     * This method checks if the query string is empty. If it is, it returns all orders from the database.
     * If the query string is not empty, it assumes the query is a username, retrieves the corresponding User
     * from the database using the UserService, and then finds and returns all orders associated with that user's ID.
     *
     * @param query The search query, typically a username.
     * @return A list of orders that match the search criteria.
     */
    public List<Order> searchOrder(String query) {

        //if(query.equals("")){
        if(query.isEmpty()){
            return orderDao.findAll();
        }

        User user = userService.findByUserName(query);
        if(user == null){
            return orderDao.findAll();
        }

        return orderDao.findOrdersByUsername(user.getId());
    }




    /**
     * This function retrieves all orders placed by a specific user, along with their details.
     * It ensures that the order details are fully loaded to avoid lazy loading issues.
     * The function is transactional to ensure the operation's atomicity.
     *
     * @param user the user whose orders are to be retrieved.
     * @return a list of orders placed by the specified user, including their details.
     */
    @Transactional
    public List<Order> getAllMyOrdersWithDetails(User user) {

        List<Order> orders = orderDao.findAll();

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


    /**
     * This function retrieves all orders from the database, along with their details.
     * It ensures that the order details are fully loaded to avoid lazy loading issues.
     * The function is transactional to ensure the operation's atomicity.
     *
     * @return a list of all orders, including their details.
     */
    @Transactional
    public List<Order> getAllOrdersWithDetails() {

        List<Order> orders = orderDao.findAll();

        // Ensure orderDetails are initialized
        for (Order order : orders) {
            order.getOrderDetails().size(); // Trigger lazy loading
        }
        return orders;
    }


    /**
     * This function converts a list of cart items into a list of order details.
     * Each cart item is mapped to a corresponding order detail, which includes
     * the book, quantity, and price. The function is transactional to ensure the operation's atomicity.
     *
     * @param cartItems the list of cart items to convert.
     * @return a list of order details derived from the provided cart items.
     */
    @Transactional
    public List<OrderDetails> convertToOrderDetails(List<CartItems> cartItems) {

//        List<OrderDetails> orderDetails = cartItems.stream().map(cartItem -> {
//            OrderDetails orderDetail = new OrderDetails();
//            orderDetail.setBook(cartItem.getBook());
//            orderDetail.setQuantity(cartItem.getQuantity());
//            orderDetail.setPrice(cartItem.getBook().getPrice());
//
//            return orderDetail;
//        }).collect(Collectors.toList());
//        return orderDetails;

        return cartItems.stream().map(cartItem -> {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setBook(cartItem.getBook());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getBook().getPrice());

            return orderDetail;
        }).collect(Collectors.toList());
    }



    /**
     * Place an order: reserve each item’s stock, then save the Order + details and clear the cart.
     * All in one @Transactional so it’s atomic from the caller’s POV.
     */
    @Transactional
    public Order placeOrder(User user, List<CartItems> cartItems) {
        // reserve stock (may throw OutOfStockException)
        for (CartItems ci : cartItems) {
            stockService.reserveStock((long) ci.getBook().getBookId(), ci.getQuantity());
        }

        // convert & save order
        List<OrderDetails> details = convertToOrderDetails(cartItems);
        Order order = createOrder(details, user);

        // clear cart
        cartService.clearCart(user);

        return order;
    }



    /**
     * This function creates a new order for a user based on a list of order details.
     * It calculates the total amount for the order, sets the order status to "Pending",
     * and associates the order with the current user. The function saves the order and its details
     * in the database. It is transactional to ensure the entire process is executed atomically.
     *
     * @param orderDetailsList the list of order details to be included in the order.
     * @param user the user for whom the order is being created.
     * @return the newly created order.
     */
    @Transactional
    public Order createOrder(List<OrderDetails> orderDetailsList , User user){

        //User currentUser = userDao.findById(user.getId());
        User currentUser = userService.findById(user.getId());

        Order order = new Order();
        order.setOrderDate(new Date());
        double totalAmount = orderDetailsList.stream().mapToDouble(detail -> detail.getPrice() * detail.getQuantity()).sum();
        order.setTotalAmount(totalAmount);
        order.setUser(currentUser);
        order.setStatus("Pending");

        orderDao.save(order);

        for (OrderDetails detail : orderDetailsList) {
            //order.add(detail);
            detail.setOrder(order);
            orderDetailsRepository.save(detail);
        }

        return order;
    }

}
