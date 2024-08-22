package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.*;
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
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    /**
     * Handles the request to display the user's shopping cart.
     * This method is mapped to the "/cart" URL and is triggered by a GET request.
     * It retrieves the current user's cart items, calculates the total amount, and
     * adds the necessary attributes to the model for displaying the cart view.
     * If the user is not logged in, it throws an exception.
     *
     * @param model The model to which the cart items, total amount, and page title will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @param authentication The authentication object to obtain the current user details.
     * @return The name of the view to display the shopping cart.
     * @throws RuntimeException if the user is not logged in.
     */
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

        return "cart/shopping_cart";
    }

    /**
     * Handles the request to remove a book from the user's cart.
     * This method is mapped to the "/remove/{id}" URL and is triggered by a POST request.
     * It removes the book from the current user's cart by its ID and sets a success message.
     * If the user is not logged in, it throws an exception. After removing the book,
     * it redirects to the cart page.
     *
     * @param id The ID of the book to be removed from the cart.
     * @param session The HTTP session from which the current user will be retrieved.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the cart page.
     * @throws RuntimeException if the user is not logged in.
     */
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

    /**
     * Handles the request to update the quantity of a book in the user's cart.
     * This method is mapped to the "/updateQuantity" URL and is triggered by a POST request.
     * It updates the quantity of the specified book in the current user's cart and reloads the cart view
     * with the updated total amount. If the user is not logged in,
     * it retrieves the current user from the session.
     *
     * @param bookId The ID of the book whose quantity is to be updated.
     * @param quantity The new quantity of the book.
     * @param session The HTTP session from which the current user will be retrieved.
     * @param principal The principal object to obtain the current user details.
     * @param model The model to which the updated cart items, total amount, and page title will be added.
     * @return The name of the view to display the updated shopping cart.
     */
    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam("bookId") Long bookId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session ,
                                 Principal principal,
                                 Model model) {

        // Get the current user
        User currentUser = userService.getCurrentUser(session);

        String title = currentUser.getUserName() + "'s Cart List";
        model.addAttribute("title", title);

        // Update the quantity in the cart
        cartService.updateQuantity(currentUser, bookId, quantity);

        // Reload the cart view
        List<CartItems> cartItemsList = cartService.getCartForUser(currentUser);
        model.addAttribute("cartItems", cartItemsList);
        double totalAmount = cartItemsList.stream().mapToDouble(item -> item.getBook().getPrice() * item.getQuantity()).sum();
        model.addAttribute("totalAmount", totalAmount);

        return "cart/shopping_cart";
    }
}
