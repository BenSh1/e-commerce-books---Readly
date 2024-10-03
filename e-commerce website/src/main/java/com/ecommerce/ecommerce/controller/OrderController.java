package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentHashMap;


import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    // ConcurrentHashMap to hold locks for each book
    private final ConcurrentHashMap<Long, Lock> bookLocks = new ConcurrentHashMap<>();


    /**
     * Handles the request to display the list of all orders.
     * This method is mapped to the "/orderList" URL and is triggered by a GET request.
     * It retrieves all orders along with their details and adds them to the model.
     * The method also sets a title attribute for the view.
     *
     * @param model The model to which the list of orders and the title will be added.
     * @return The name of the view to display the list of all orders.

     */
    @GetMapping("/orderList")
    public String getOrderListPage(Model model) {
        List<Order> orders = orderService.getAllOrdersWithDetails();
        model.addAttribute("orders", orders);

        String title = " Order List";
        model.addAttribute("title", title);

        return "order/orderList";
    }

    /**
     * Handles the request to search for orders in the order list.
     * This method is mapped to the "/searchInOrderList" URL and is triggered by a GET request.
     * It retrieves orders that match the search query and adds them to the model.
     *
     * @param query The search query used to find orders.
     * @param model The model to which the list of matching orders will be added.
     * @return The name of the view to display the search results.
     */
    @GetMapping("/searchInOrderList")
    public String searchForOrderInOrdersList(@RequestParam("query") String query,
                                              Model model) {

        List<Order> orders = orderService.searchOrder(query);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    /**
     * Handles the request to display the list of orders for the current user.
     * This method is mapped to the "/myOrderList" URL and is triggered by a GET request.
     * It retrieves the current user's orders along with their details and adds them to the model.
     * If the user is not logged in, it throws an exception.
     *
     * @param model     The model to which the list of orders and the title will be added.
     * @param session   The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the current user's order list.
     * @throws RuntimeException if the user is not logged in.
     */
    @GetMapping("/myOrderList")
    public String getOrderListPerUser(Model model ,HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<Order> orders = orderService.getAllMyOrdersWithDetails(currentUser);
        model.addAttribute("orders", orders);
        String title = currentUser.getUserName() + "'s Order List";
        model.addAttribute("title", title);

        return "order/myOrderList";
    }

    /**
     * Handles the request to update the status of an order to "Supplied".
     * This method is mapped to the "/updateToSupplied/{id}" URL and is triggered by a POST request.
     * It updates the status of the specified order to "Supplied", saves the updated order,
     * and adds a success message to the redirect attributes. The method then redirects to the order list page.
     *
     * @param model The model to which attributes for redirection may be added.
     * @param id The ID of the order whose status is to be updated.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the order list page.
     */
    @Transactional
    @PostMapping("/updateToSupplied/{id}")
    public String updateToSupplied(Model model ,
                                   @PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {

        Order theOrder = orderService.getOrder(id);

        //theOrder.setStatus("The Order has been supplied");
        theOrder.setStatus("Supplied!");

        orderService.addOrder(theOrder);

        redirectAttributes.addFlashAttribute("SuppliedMessage", "The Order has been supplied!");

        return "redirect:/orderList";
    }

    /**
     * Handles the request to create a new order based on the current user's cart items.
     * This method is mapped to the "/addOrder" URL and is triggered by a POST request.
     * It retrieves the current user's cart items, updates the stock for each book, creates an order
     * with the order details, and clears the cart. The order confirmation page is then returned.
     * If the user is not logged in, it throws an exception.
     *
     * @param model             The model to which the order object will be added.
     * @param session           The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the order confirmation.
     * @throws RuntimeException if the user is not logged in or if a book in the cart is out of stock.

     */
    /*
    @PostMapping("/addOrder")
    @Transactional
    public String addOrder(Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        //update the stock for each book in the cartItems
        for(CartItems cartItem : cartItems) {

            Book book = bookDao.findByIdWithLock((long)cartItem.getBook().getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getStock() < cartItem.getQuantity()) {
                if(cartItem.getQuantity() == 1){
                    cartService.removeBookFromCart(book.getBookId(),currentUser);
                }
                model.addAttribute("titleOfBook", book.getTitle());
                return "books/bookIsOutOfStock";
            }

            book.setStock(book.getStock() - cartItem.getQuantity());
            bookService.addBook(book);
        }

        List<OrderDetails> orderDetails = orderService.convertToOrderDetails(cartItems);

        Order order = orderService.createOrder(orderDetails, currentUser);
        model.addAttribute("order", order);

        cartService.clearCart(currentUser);

        return "order/orderConfirmation";
    }

     */


    @PostMapping("/addOrder")
    @Transactional
    public String addOrder(Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        //update the stock for each book in the cartItems
        for(CartItems cartItem : cartItems) {

            int bookId = cartItem.getBook().getBookId();
            Lock lock = bookLocks.computeIfAbsent((long)bookId, id -> new ReentrantLock());

            // Attempt to acquire the lock for the book
            lock.lock();
            try {
                // Fetch the book with a pessimistic lock (if you want to use database-level locking too)
                Book book = bookDao.findByIdWithLock((long)bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                if (book.getStock() < cartItem.getQuantity()) {
                    // Handle out-of-stock scenario
                    if (cartItem.getQuantity() == 1) {
                        cartService.removeBookFromCart(book.getBookId(), currentUser);
                    }
                    model.addAttribute("titleOfBook", book.getTitle());
                    return "books/bookIsOutOfStock";
                }
                // Update the stock
                book.setStock(book.getStock() - cartItem.getQuantity());
                bookService.addBook(book);
            }finally {
                // Always release the lock after operation is complete
                lock.unlock();
                // Optionally clean up the lock to avoid memory leaks for rarely sold books
                bookLocks.remove((long)bookId);
            }
        }

        List<OrderDetails> orderDetails = orderService.convertToOrderDetails(cartItems);

        Order order = orderService.createOrder(orderDetails, currentUser);
        model.addAttribute("order", order);

        cartService.clearCart(currentUser);

        return "order/orderConfirmation";
    }

}
