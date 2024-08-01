package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.ServiceController;
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

    @Autowired
    private ServiceController customerService;

    @GetMapping("/")
    public String getLandingPage(Model model) {
        List<Book> allBooks = bookService.getBooks();
        model.addAttribute("books", allBooks);
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

    @GetMapping("/page1")
    public String getPage1(){
        return "page1";
    }

    @GetMapping("/page2")
    public String getPage2(){
        return "page2";
    }
    /*
    @GetMapping("/register")
    public String getPageRegistration(Model model){
        model.addAttribute("customer", new Customer());

        return "registration";
    }

    @PostMapping("/successfulRegister")
    public String submitForm(@ModelAttribute("customer") Customer customer, Model model) {
        // Handle form data

        //model.addAttribute("customer", customer);

        //System.out.println("customer id :  " + customer.getCustomerId());
        customerService.saveCustomer(customer);
        //customerDAO.save(customer);
        return "registerSuccessed";
    }

     */



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

/*
    @GetMapping("/login")
    public String getPageLogin(Model model){
        //model.addAttribute("customer", new Customer());
        return "login";
    }



    @PostMapping("/successfulLogin")
    public String getResultLogin(@RequestParam("userName") String userName,
                                 @RequestParam("password") String password,
                                 Model model) {
        // Handle form data
        //customerService.getCustomer(userName,password);
        //model.addAttribute("customer", customer);

        System.out.println("customer userName :  " + userName);
        System.out.println("customer password :  " + password);
        List<Customer> theCustomers = customerService.queryForCustomersByUserNameAndPassword(userName , password);
        //model.addAttribute("customer", theCustomers.getFirst());

        if (theCustomers != null)
        {
            // Add the first customer to the model
            if (!theCustomers.isEmpty()) {
                model.addAttribute("customer", theCustomers.getFirst());
            }
            return "loginSuccessed";
        }
        return "loginFail";


    }
*/


}
