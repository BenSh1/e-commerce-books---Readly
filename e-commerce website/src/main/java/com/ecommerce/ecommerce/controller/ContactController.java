package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.service.ContactInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    private final ContactInfoService contactInfoService;

    public ContactController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }


    /**
     * Handles GET requests to the "/contact" URL.
     * This method populates the model with the manager's contact information
     * by adding the manager's email and phone number to the model attributes.
     * It then returns the "contact" view name to be rendered.
     *
     * @param model The Model object used to pass data to the view.
     * @return The name of the view to render, in this case, "contact".
     */
    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("managerEmail", contactInfoService.getManagerEmail());
        model.addAttribute("managerPhone", contactInfoService.getManagerPhone());
        return "contact";
    }
}
