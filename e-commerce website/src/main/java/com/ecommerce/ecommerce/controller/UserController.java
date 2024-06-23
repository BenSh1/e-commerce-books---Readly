package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.dao.UserDaoImpl;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    /*
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

     */

    @Value("${roles}")
    private List<String> roles;
    @Autowired
    private UserDaoImpl userDaoImpl;

    @GetMapping("/customersList")
    public String customersList(Model model) {
        List<User> users = userService.getUsers();
        //List<WebUser> users = userService.getUsers();
        model.addAttribute("customers", users);
        return "customersList"; // Return the view to display the books
    }

    @GetMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable Long id,
                               Model model) {
        //WebUser webUser = userService.getCustomer(id);
        User user = userService.getUser(id);
        model.addAttribute("user", user);

        // add the list of languages to the model
        model.addAttribute("roles",roles);



        return "editCustomer";
    }


    @PostMapping("/editCustomer/{id}")
    public String updateCustomer(Model model , @PathVariable Long id,
                                 @ModelAttribute User theUser,
                                 RedirectAttributes redirectAttributes,
                                @RequestParam String role) {

        System.out.println("---------------role : " + role);

        userService.update(id, theUser, role);
        redirectAttributes.addFlashAttribute("message", "user updated successfully!");

        return "redirect:/customersList";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/customersList";
    }

    @PostMapping("/updateRankUser/{id}")
    public String updateRankUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        /*
        userService.deleteUser(id);

        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        */
        return "redirect:/customersList";

    }




}
