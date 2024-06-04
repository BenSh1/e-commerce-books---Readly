package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.Data_Transfer_Object.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ECommerceController {

    @GetMapping("/greeting")
    public String helloWorld(){
        return "Hello World from Spring Boot!";
    }


    @GetMapping("/goodbye")
    public String goodbye(){
        return "Goodbye from Spring Boot!";
    }

    @GetMapping("/")
    public String getHtmPage(){
        return "index";
    }

    @GetMapping("/page1")
    public String getPage1(){
        return "page1";
    }

    @GetMapping("/page2")
    public String getPage2(){
        return "page2";
    }

    @GetMapping("/register")
    public String getPageRegistration(){
        return "registration";
    }

    @GetMapping("/register2")
    public String getPageRegistration2(Model model){
        model.addAttribute("formData", new FormData());
        return "registration2";
    }

    @GetMapping("/register3")
    public String getPageRegistration3(Model model){
        model.addAttribute("formData", new FormData());
        return "registration3";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute FormData formData, Model model) {
        // Handle form data
        model.addAttribute("formData", formData);
        return "registerSuccessed";
    }


    @GetMapping("/login")
    public String getPageLogin(){
        return "Login";
    }



}
