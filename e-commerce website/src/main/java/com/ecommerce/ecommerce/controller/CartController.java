package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;


    @GetMapping("/cart")
    public String showShoppingCart(Model model, HttpSession session ,
                                   @AuthenticationPrincipal Authentication authentication)
    {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        // Calculate the total amount
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");
        model.addAttribute("totalAmount",totalAmount);

        String title = currentUser.getUserName() + "'s Cart List";
        model.addAttribute("title", title);

        return "shopping_cart";
    }

    @PostMapping("/remove/{id}")
    public String removeBookInCart(@PathVariable Integer id,
                                   HttpSession session ,
                                   RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

        cartService.removeBookFromCart(id, currentUser);
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
        return "redirect:/cart";
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("bookId") Long bookId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session ,
                                 Principal principal,
                                 Model model) {

        // Get the current user
        User currentUser = getCurrentUser(session);

        // Update the quantity in the cart
        cartService.updateQuantity(currentUser, bookId, quantity);

        // Reload the cart view
        List<CartItems> cartItemsList = cartService.getCartForUser(currentUser);
        model.addAttribute("cartItems", cartItemsList);
        double totalAmount = cartItemsList.stream().mapToDouble(item -> item.getBook().getPrice() * item.getQuantity()).sum();
        model.addAttribute("totalAmount", totalAmount);

        return "shopping_cart";
    }

    private User getCurrentUser(HttpSession session) {
        // Get the current user
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        return currentUser;
    }

}
