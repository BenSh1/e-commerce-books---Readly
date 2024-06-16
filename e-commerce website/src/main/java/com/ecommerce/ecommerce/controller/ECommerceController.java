package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.ServiceController;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;


@Controller
public class ECommerceController {


    @Autowired
    private ServiceController customerService;

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

    @Autowired
    public ECommerceController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage() {
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
