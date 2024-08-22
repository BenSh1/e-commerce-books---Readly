package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ECommerceController {

    @Autowired
    private BookService bookService;


    /**
     * Handles GET requests to the root URL ("/").
     *
     * This method checks if a user is logged in by retrieving the "user" attribute from the session.
     * If the user is logged in, it adds the current user to the model attributes.
     *
     * The method also retrieves a list of all books and selects the first four books to display on the landing page.
     * These books are added to the model attributes before returning the "landing" view name to be rendered.
     *
     * @param model The Model object used to pass data to the view.
     * @param session The HttpSession object used to retrieve session attributes.
     * @return The name of the view to render, in this case, "landing".
     */
    @GetMapping("/")
    public String getLandingPage(Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            model.addAttribute("currentUser",currentUser);
        }


        List<Book> allBooks = bookService.getBooks();
        List<Book> firstFourBooks = allBooks.stream()
                .limit(4)
                .toList();

        model.addAttribute("books", firstFourBooks);
        return "landing";
    }

    /**
     * Handles GET requests to the "/menuForManager" URL.
     * This method checks if a user is logged in by retrieving the "user" attribute from the session.
     * If the user is logged in, it adds the current user to the model attributes.
     * It returns the "menuForManager" view name to be rendered.
     *
     * @param model The Model object used to pass data to the view.
     * @param session The HttpSession object used to retrieve session attributes.
     * @return The name of the view to render, in this case, "menuForManager".
     */
    @GetMapping("/menuForManager")
    public String showMenuOfManager(Model model , HttpSession session){

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            model.addAttribute("currentUser",currentUser);
        }
        return "menuForManager";
    }


}
