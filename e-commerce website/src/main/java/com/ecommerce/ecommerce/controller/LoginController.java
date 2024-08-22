package com.ecommerce.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Handles GET requests to the "/showMyLoginPage" URL.
     *
     * This method returns the view name for the login page.
     * It is used to display the custom login page to the user.
     *
     * @return The name of the view to render, in this case, "login".
     */
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {

        return "login";
    }

    /**
     * Handles GET requests to the "/access-denied" URL.
     *
     * This method returns the view name for the access denied page.
     * It is used to display a message to the user when they try to access a resource they are not authorized to view.
     *
     * @return The name of the view to render, in this case, "access-denied".
     */
    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

}
