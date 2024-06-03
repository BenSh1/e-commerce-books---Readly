package com.ecommerce.ecommerce;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
