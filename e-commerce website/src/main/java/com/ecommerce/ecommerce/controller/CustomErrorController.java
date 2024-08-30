package com.ecommerce.ecommerce.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {


    /**
     * Handles errors by returning the name of the generic error page.
     *
     * @return the view name "error" to be rendered when an error occurs.
     */
    @RequestMapping("/error")
    public String handleError() {
        return "error";// Generic error page
    }

    /**
     * Handles GET requests to the "/access-denied" URL.
     *
     * This method returns the view name for the access denied page.
     * It is used to display a message to the user when they try to access a resource they are not authorized to view.
     *
     * @return The name of the view to render, in this case, "access-denied".
     */
    @RequestMapping("/access-denied")
    public String accessDenied() {

        return "access-denied"; // Custom 403 error page
    }
}



