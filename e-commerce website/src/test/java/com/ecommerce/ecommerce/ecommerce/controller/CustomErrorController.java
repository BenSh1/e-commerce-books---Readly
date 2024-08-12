package com.ecommerce.ecommerce.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // You can use request attributes or model to pass additional information
        return "error"; // Generic error page
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Custom 403 error page
    }

    @RequestMapping("/404")
    public String notFound() {
        return "404"; // Custom 404 error page
    }

    // Implementing the method from ErrorController interface
    //@Override
    public String getErrorPath() {
        return "/error";
    }
}



