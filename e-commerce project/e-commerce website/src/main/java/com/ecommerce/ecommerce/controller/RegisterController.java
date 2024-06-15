package com.ecommerce.ecommerce.controller;
/*
import com.ecommerce.ecommerce.ServiceController;
import com.ecommerce.ecommerce.entity.Customer;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {



    @Autowired
    private ServiceController userService;


    @GetMapping("/register")
    public String getPageRegistration(Model model){
        model.addAttribute("customer", new Customer());

        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("customer") Customer customer) {
        User user = new User();
        user.setUsername(customer.getUserName());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));
        user.setEnabled(true);
        userService.saveUser(user);
        return "redirect:/login";
    }




}
*/