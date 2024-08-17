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
import java.util.stream.Collectors;


@Controller
public class ECommerceController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getLandingPage(Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);
        }


        List<Book> allBooks = bookService.getBooks();
        List<Book> firstFourBooks = allBooks.stream()
                .limit(4)
                .toList();

        model.addAttribute("books", firstFourBooks);
        return "landing";
    }

    @GetMapping("/home")
    public String getHomePage(Model model , HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("===currentUser.getFirstName()========================= : " +currentUser.toString());

        model.addAttribute("user", currentUser);
        return "home";
    }

    // add a request mapping for /managers
    @GetMapping("/menuForManager")
    public String showMenuOfManager(Model model , HttpSession session){

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);
        }

        return "menuForManager";
    }

    // add a request mapping for /leaders
    @GetMapping("/leaders")
    public String showLeaders() {
        return "leaders";
    }

    // add a request mapping for /systems
    @GetMapping("/systems")
    public String showSystems() {
        return "systems";
    }

}
