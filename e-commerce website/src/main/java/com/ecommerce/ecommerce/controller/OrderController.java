package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * Handles the request to display the list of all orders.
     * This method is mapped to the "/orderList" URL and is triggered by a GET request.
     * It retrieves all orders along with their details and adds them to the model.
     * The method also sets a title attribute for the view.
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
     * Handles the request to display the list of orders for the current user.
     * This method is mapped to the "/myOrderList" URL and is triggered by a GET request.
     * It retrieves the current user's orders along with their details and adds them to the model.
     * If the user is not logged in, it throws an exception.
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
     */
    @Transactional
    @PostMapping("/updateToSupplied/{id}")
    public String updateToSupplied(Model model ,
                                   @PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {

        Order theOrder = orderDao.findOrderById(id);
        //theOrder.setStatus("The Order has been supplied");
        theOrder.setStatus("Supplied!");

        orderDao.save(theOrder);
        redirectAttributes.addFlashAttribute("SuppliedMessage", "The Order has been supplied!");

        return "redirect:/orderList";
    }

    /**
     * Handles the request to create a new order based on the current user's cart items.
     * This method is mapped to the "/addOrder" URL and is triggered by a POST request.
     * It retrieves the current user's cart items, updates the stock for each book, creates an order
     * with the order details, and clears the cart. The order confirmation page is then returned.
     * If the user is not logged in, it throws an exception.
     */
    @PostMapping("/addOrder")
    public String addOrder(Model model, HttpSession session ,
                           @AuthenticationPrincipal Authentication authentication) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("currentUser.getFirstName()  = " + currentUser.getFirstName());


        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        //update the stock for each book in the cartItems
        for(CartItems cartItem : cartItems) {
            Book book = bookDao.findById((long)cartItem.getBook().getBookId());
            book.setStock(book.getStock() - cartItem.getQuantity());
            bookDao.save(book);
        }

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

        return "order/orderConfirmation";
    }

}
