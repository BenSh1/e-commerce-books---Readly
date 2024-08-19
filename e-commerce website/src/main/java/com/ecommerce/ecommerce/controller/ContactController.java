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

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("managerEmail", contactInfoService.getManagerEmail());
        model.addAttribute("managerPhone", contactInfoService.getManagerPhone());
        return "contact";
    }
}
