package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.RoleService;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {

    final int SECOND_ITEM_IN_ROLES_LIST  = 1;
    final int THIRD_ITEM_IN_ROLES_LIST  = 2;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Value("${roles}")
    private List<String> roles;

    /*
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
     */

    /**
     * Displays the list of customers.
     * This method is mapped to the "/customersList" URL and is triggered by a GET request.
     * It retrieves the list of users from the userService and adds it to the model.
     * The view to display the customers is then returned.
     */
    @GetMapping("/customersList")
    public String customersList(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("customers", users);

        return "user/customersList";// Return the view to display the books
    }

    @GetMapping("/searchInCustomerList")
    public String searchForCustomerInUserList(@RequestParam("query") String query,
                                              Model model) {

        List<User> customers = userService.searchUsers(query);
        model.addAttribute("customers", customers);

        return "/user/customersList";
    }

    /**
     * Displays the edit customer page for a specific user.
     * This method is mapped to the "/editCustomer/{id}" URL and is triggered by a GET request.
     * It retrieves the user by the provided ID and adds it to the model for editing.
     * The current user's role is also determined and added to the model for use in the view.
     */
    @GetMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable Long id,
                               /*@Valid @ModelAttribute("user") User theUser,*/
                               Model model,
                               HttpSession session) {
        //WebUser webUser = userService.getCustomer(id);
        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User currentUser = userService.getCurrentUser(session);

        //System.out.println("===========in editCustomer ===========");
        //System.out.println(currentUser.toString());
        //System.out.println("currentUser.getRoles().size() = "+ currentUser.getRoles().size());

        if ( currentUser.getRoles().size() == 3)
            model.addAttribute("currentUserRole ", "ROLE_ADMIN");
        else if( currentUser.getRoles().size() == 2) {
            model.addAttribute("currentUserRole" , "ROLE_MANAGER");
        }
        else{
            model.addAttribute("currentUserRole ", "ROLE_CUSTOMER");
        }

        // add the list of languages to the model
        model.addAttribute("roles",roles);

        return "user/editCustomer";
    }

/*
    @PostMapping("/editCustomer/{id}")
    public String updateCustomer(Model model , @PathVariable Long id,
                                 @ModelAttribute User theUser,
                                 RedirectAttributes redirectAttributes,
                                @RequestParam String role) {

        System.out.println("=======================role================ : " + role);

        userService.update(id, theUser, role);
        redirectAttributes.addFlashAttribute("message", "user updated successfully!");

        return "redirect:/customersList";
    }

 */

    /*
        edit with the same role of the member of the site.
     */

    /**
     * Updates the customer information.
     * This method is mapped to the "/editCustomer/{id}" URL and is triggered by a POST request.
     * It updates the user's information and their role, then redirects back to the edit page.
     */
    @Transactional
    @PostMapping("/editCustomer/{id}")
    public String updateCustomer(Model model , @PathVariable Long id,
                                 @ModelAttribute User theUser,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {


        User user = userService.getUser(id);

        Collection<Role> rolesOfCurrentUser = user.getRoles();

        List<Role> list = new ArrayList<>(rolesOfCurrentUser);
        System.out.println("=======list.getLast()=====:" + list.getLast());

        userService.update(id, theUser, list.getLast().getName());

        redirectAttributes.addFlashAttribute("updateMessage", "user updated successfully!");

        return "redirect:/editCustomer/{id}";
    }


/*
    @PostMapping("/editCustomer/{id}")
    public String updateCustomer2(@Valid @ModelAttribute("user") User user,
                                  BindingResult theBindingResult,
                                  @PathVariable Long id,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        System.out.println("==================in updateCustomer2 ===========");

        if (theBindingResult.hasErrors()) {
            System.out.println("==================in hasErrors ===========");
            model.addAttribute("user", user);
            //return "editCustomerForm"; // Replace with your form view name
            return "redirect:/editCustomer/" + id;
        }

        userService.update(id, user);
        redirectAttributes.addFlashAttribute("updateMessage", "The User has been updated successfully!");
        return "redirect:/editCustomer/" + id;
    }

 */



/*
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

 */

/*
    @PostMapping("/editCustomer/{id}")
    public String updateCustomer2(Model model , @PathVariable Long id,
                                 RedirectAttributes redirectAttributes,
                                @Valid @ModelAttribute("user") User user,
                                BindingResult theBindingResult) {

        System.out.println("==================in editCustomer ===========");

        // Handle form data
        String userName = user.getUserName();
        logger.info("Processing EDIT form for: " + userName);

        User tempUser = userService.getUser(id);
        logger.info("Processing EDIT form for: " + tempUser.getUserName());


        // form validation
        if (theBindingResult.hasErrors()){
            System.out.println("==================in hasErrors ===========");
            User currentUser = userService.getUser(id);
            model.addAttribute("user", currentUser);
            //return "redirect:/home";
            return "redirect:/editCustomer/" + id;
        }

        //userService.update(id, theUser);
        User currentUser = userService.getUser(id);
        userService.update(id, user);

        redirectAttributes.addFlashAttribute("updateMessage", "The User has been updated successfully!");

        //return "redirect:/editCustomer";
        return "redirect:/editCustomer/" + id;

    }

 */

    /**
     * Updates the user's role to "Customer".
     * This method is mapped to the "/updateRankToCustomer/{id}" URL and is triggered by a POST request.
     * It assigns the "Customer" role to the user and saves the changes in the database.
     */
    @PostMapping("/updateRankToCustomer/{id}")
    public String updateRankToCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleService.getRoles();

        userRoles.add(r.getFirst());
        theUser.setRoles(userRoles);


        userService.addUser(theUser);

        return "redirect:/customersList";
    }


    /**
     * Updates the user's role to "Manager".
     * This method is mapped to the "/updateRankToManager/{id}" URL and is triggered by a POST request.
     * It assigns the "Manager" role to the user and saves the changes in the database.
     */
    @PostMapping("/updateRankToManager/{id}")
    public String updateRankToManager(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleService.getRoles();

        userRoles.add(r.getFirst());
        userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
        theUser.setRoles(userRoles);

        userService.addUser(theUser);

        return "redirect:/customersList";

    }

    /**
     * Updates the user's role to "Admin".
     * This method is mapped to the "/updateRankToAdmin/{id}" URL and is triggered by a POST request.
     * It assigns the "Admin" role to the user and saves the changes in the database.
     */
    @PostMapping("/updateRankToAdmin/{id}")
    public String updateRankToAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleService.getRoles();

        userRoles.add(r.getFirst());
        userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
        userRoles.add(r.get(THIRD_ITEM_IN_ROLES_LIST));

        theUser.setRoles(userRoles);
        userService.addUser(theUser);

        return "redirect:/customersList";

    }

    /**
     * Deletes a user by ID.
     * This method is mapped to the "/deleteUser/{id}" URL and is triggered by a POST request.
     * It deletes the user from the database and redirects to the customers list page with a success message.
     */
    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/customersList";
    }

    /**
     * Displays the change password page.
     * This method is mapped to the "/changePassword" URL and is triggered by a GET request.
     * It initializes a PasswordChangeDto object and adds it to the model, then returns the view to display the change password form.
     */
    @GetMapping("/changePassword")
    public String changePassword(  Model model) {

        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        model.addAttribute("passwordChangeDto",passwordChangeDto);

        return "user/changePassword";

    }

    /**
     * Handles the password change process.
     * This method is mapped to the "/changePassword" URL and is triggered by a POST request.
     * It takes the password change details from the form, attempts to change the user's password, and returns the appropriate view with a success or error message.
     */
    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute PasswordChangeDto passwordChangeDto, Principal principal, Model model) {
        String username = principal.getName();
        boolean success = userService.changeUserPassword(username, passwordChangeDto);

        if (success) {
            model.addAttribute("message", "Password changed successfully.");
        } else {
            model.addAttribute("error", "Password change failed. Please try again.");
        }
        return "user/changePassword";
    }



    @GetMapping("/changePasswordByAdmin/{id}")
    public String changePasswordByAdmin(@PathVariable Long id,  Model model) {

        User currentUser = userService.getUser(id);
        model.addAttribute("user",currentUser);

        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        model.addAttribute("passwordChangeDto",passwordChangeDto);

        return "user/changePasswordByAdmin";
    }

    @PostMapping("/changePasswordByAdmin/{id}")
    public String changePasswordByAdmin(@PathVariable Long id,
                                        @ModelAttribute PasswordChangeDto passwordChangeDto,
                                        Model model) {

        User currentUser = userService.getUser(id);

        boolean isSuccess = userService.changeUserPasswordByAdmin(currentUser.getUserName(), passwordChangeDto);

        System.out.println("=======in changePasswordByAdmin=======================");
        System.out.println("currentUser.getUserName() : " + currentUser.getUserName());

        model.addAttribute("user",currentUser);

        if (isSuccess) {
            model.addAttribute("message", "Password changed successfully.");
        } else {
            model.addAttribute("error", "Password change failed. Please try again.");
        }
        return "user/changePasswordByAdmin";
    }

/*
    @GetMapping("/forgetPassword")
    public String getForgotPassword() {

        return "user/forgetPassword";
    }

 */
/*
    @PostMapping("/forgotPassword")
    public String processForgotPassword(Model model, String email) {
        // Find user by email
        User user = userService.findUserByEmail(email);

        if (user == null) {
            model.addAttribute("error", "No account found with that email.");
            return "forgotPassword";
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        // Create the email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setText("To reset your password, click the link below:\n"
                + "http://localhost:8080/resetPassword?token=" + token);

        // Send the email
        mailSender.send(mailMessage);

        model.addAttribute("message", "Password reset link sent to your email.");
        return "user/forgotPassword";
    }

 */



}
