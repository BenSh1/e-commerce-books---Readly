package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        //User user = userService.get
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("currentUser.getFirstName()  = " + currentUser.getFirstName());

        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        // Calculate the total amount
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();


        model.addAttribute("cartItems", cartItems);
        model.addAttribute("pageTitle", "Shopping Cart");
        model.addAttribute("totalAmount",totalAmount);


        return "shooping_cart";
    }

    @PostMapping("/remove/{id}")
    public String removeBookInCart(@PathVariable Integer id,
                                   HttpSession session ,
                                   RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        //User user = userService.get
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("currentUser.getFirstName()  = " + currentUser.getFirstName());


        cartService.removeBookFromCart(id, currentUser);
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
        return "redirect:/cart";
    }




    @GetMapping("/cart2")
    public String showShoppingCart2(/*
    @AuthenticationPrincipal UserDetails userDetails,*/
            @AuthenticationPrincipal Authentication authentication,
                                   Model model) {

        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //userService.getCurrentCustomerUsername();
/*
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByUserName(username);
            List<CartItems> cartItems = cartService.getCartForUser(user);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("pageTitle", "Shopping Cart");

            return "shoppingCart";

        }

 */
        return "access-denied";

    }




/*
    //@RequestParam("bookId") Long bookId
    @PostMapping("/cart/add/{bookId}")
    public String addToCart( Model model,@PathVariable Long bookId)
    {

        Book book = bookDao.findById(bookId);
        CartItems cartItems = (CartItems) model.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new CartItems();
            model.addAttribute("cartItems", cartItems);
        }

        //cartService.addToCart();
        cartItems.addBook(book);
        return "redirect:/cart";
    }

 */











}
