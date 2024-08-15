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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /*
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private UserDao userDao;

     */

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;


    @GetMapping("/orderList")
    public String getOrderListPage(Model model) {
        List<Order> orders = orderService.getAllOrdersWithDetails();
        model.addAttribute("orders", orders);

        String title = " Order List";
        model.addAttribute("title", title);

        return "orderList";
    }

    @GetMapping("/myOrderList")
    public String getOrderListPerUser(Model model ,HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        System.out.println("===currentUser.getFirstName()========================= : " +currentUser.toString());

        List<Order> orders = orderService.getAllMyOrdersWithDetails(currentUser);
        model.addAttribute("orders", orders);
        String title = currentUser.getUserName() + "'s Order List";
        model.addAttribute("title", title);

        return "myOrderList";
    }

    @Transactional
    @PostMapping("/updateToSupplied/{id}")
    public String updateToSupplied(Model model ,
                                   @PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {

        Order theOrder = orderDao.findOrderById(id);
        //theOrder.setStatus("The Order has been supplied");
        theOrder.setStatus("Supplied!");

        //orderRepository.save(theOrder);
        orderDao.save(theOrder);
        System.out.println("===============================================");
        redirectAttributes.addFlashAttribute("SuppliedMessage", "The Order has been supplied!");

        return "redirect:/orderList";
    }

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

        return "orderConfirmation";
    }




}
