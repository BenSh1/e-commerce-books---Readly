package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.UserConverter;
import com.ecommerce.ecommerce.dao.UserDaoImpl;
import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.dto.WebUser;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.RoleService;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    /**
     * Displays the list of customers.
     * This method is mapped to the "/customersList" URL and is triggered by a GET request.
     * It retrieves the list of users from the userService and adds it to the model.
     * The view to display the customers is then returned.
     *
     * @param model The model to which the list of customers will be added.
     * @return The name of the view to display the list of customers.
     */
    @GetMapping("/customersList")
    public String customersList(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("customers", users);

        return "user/customersList";// Return the view to display the books
    }

    /**
     * Searches for customers in the user list based on a query string.
     * This method is mapped to the "/searchInCustomerList" URL and is triggered by a GET request.
     * It retrieves a list of users matching the search query and adds it to the model.
     * The view to display the search results is then returned.
     *
     * @param query The search query string used to find customers.
     * @param model The model to which the list of matching customers will be added.
     * @return The name of the view to display the search results.
     */
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
     *
     * @param id        The ID of the user to be edited.
     * @param model     The model to which the user and current user's role will be added.
     * @param session   The HTTP session from which the current user information is retrieved.
     * @return The name of the view to display the edit customer form.

     */
    @GetMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable Long id,
                               Model model,
                               HttpSession session) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User currentUser = userService.getCurrentUser(session);

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


    @GetMapping("/editCustomerWithWebUser")
    public String editCustomerPerUser(Model model,
                               HttpSession session) {

        User user = userService.getCurrentUser(session);

        // Convert User entity to WebUser DTO
        //WebUser webUser = UserConverter.convertToWebUser(user);
        WebUser webUser = userService.convertToWebUser(user);
        model.addAttribute("webUser", webUser);

        User currentUser = userService.getCurrentUser(session);
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

        return "user/editCustomerWithWebUser";
    }

    // Handle profile form submission
    @PostMapping("/editCustomerWithWebUser")
    public String processProfileForm(
            @Valid @ModelAttribute("webUser") WebUser webUser,
            BindingResult bindingResult, Model model, HttpSession session,
            @RequestParam(name = "currentPassword", defaultValue = "notProvided") String currentPassword ) {

        // Get the currently logged-in user
        User user = userService.getCurrentUser(session);

        // Convert WebUser DTO back to User entity
        //User currentUser = UserConverter.convertToUser(webUser, user);
        User currentUser = userService.convertToUser(webUser, user);

        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form page
            System.out.println("======there is an error!!!!===============");
            model.addAttribute("webUser", webUser);
            return "user/editCustomerWithWebUser";
        }
        System.out.println("=======================checking ====================: ");

        // Save the updated user entity
        userService.save(currentUser);

        // Redirect to profile page or display success message
        return "redirect:/editCustomerWithWebUser";
    }




    /**
     * Updates the customer information.
     * This method is mapped to the "/editCustomer/{id}" URL and is triggered by a POST request.
     * It updates the user's information and their role, then redirects back to the edit page.
     *
     * @param id                    The ID of the user to be updated.
     * @param theUser               The user information to be updated.
     * @param redirectAttributes    The redirect attributes used to add flash attributes for messages.
     * @return The redirect URL to the edit customer page.
     */
    @Transactional
    @PostMapping("/editCustomer/{id}")
    public String updateCustomer( @PathVariable Long id,
                                 @ModelAttribute User theUser,
                                 RedirectAttributes redirectAttributes) {
        User user = userService.getUser(id);

        Collection<Role> rolesOfCurrentUser = user.getRoles();

        List<Role> list = new ArrayList<>(rolesOfCurrentUser);

        userService.update(id, theUser, list.getLast().getName());

        redirectAttributes.addFlashAttribute("updateMessage", "user updated successfully!");

        return "redirect:/editCustomer/{id}";
    }

    /**
     * Updates the user's role to "Customer".
     * This method is mapped to the "/updateRankToCustomer/{id}" URL and is triggered by a POST request.
     * It assigns the "Customer" role to the user and saves the changes in the database.
     *
     * @param id                    The ID of the user whose role is to be updated.
     * @param redirectAttributes    The redirect attributes used to add flash attributes for success messages.
     * @return The redirect URL to the customers list page.

     */
    @PostMapping("/updateRankToCustomer/{id}")
    public String updateRankToCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleService.getRoles();

        userRoles.add(r.getFirst());
        theUser.setRoles(userRoles);

        userService.addUser(theUser);
        redirectAttributes.addFlashAttribute("message", "User updated successfully to the customer rank!");

        return "redirect:/customersList";
    }


    /**
     * Updates the user's role to "Manager".
     * This method is mapped to the "/updateRankToManager/{id}" URL and is triggered by a POST request.
     * It assigns the "Manager" role to the user and saves the changes in the database.
     *
     * @param id                    The ID of the user whose role is to be updated.
     * @param redirectAttributes    The redirect attributes used to add flash attributes for success messages.
     * @return The redirect URL to the customers list page.
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
        redirectAttributes.addFlashAttribute("message", "User updated successfully to the manager rank!");

        return "redirect:/customersList";

    }

    /**
     * Updates the user's role to "Admin".
     * This method is mapped to the "/updateRankToAdmin/{id}" URL and is triggered by a POST request.
     * It assigns the "Admin" role to the user and saves the changes in the database.
     *
     * @param id                    The ID of the user whose role is to be updated.
     * @param redirectAttributes    The redirect attributes used to add flash attributes for success messages.
     * @return The redirect URL to the customers list page.
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
        redirectAttributes.addFlashAttribute("message", "User updated successfully to the admin rank!");

        return "redirect:/customersList";

    }

    /**
     * Deletes a user by ID.
     * This method is mapped to the "/deleteUser/{id}" URL and is triggered by a POST request.
     * It deletes the user from the database and redirects to the customers list page with a success message.
     *
     * @param id                    The ID of the user to be deleted.
     * @param redirectAttributes    The redirect attributes used to add flash attributes for success messages.
     * @return The redirect URL to the customers list page.
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
     * It initializes a PasswordChangeDto object and adds it to the model,
     * then returns the view to display the change password form.
     *
     * @param model The model to which the PasswordChangeDto object will be added.
     * @return The name of the view to display the change password form.
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
     * It takes the password change details from the form, attempts to change the user's password,
     * and returns the appropriate view with a success or error message.
     *
     * @param passwordChangeDto The DTO containing the new password information.
     * @param principal         The principal object representing the currently authenticated user.
     * @param model             The model to which success or error messages will be added.
     * @return The name of the view to display the password change form with a success or error message.

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


    /**
     * Handles the GET request for changing a user's password by an admin.
     * This method retrieves the user with the given ID from the service layer
     * and adds the user to the model. It also creates a new instance of
     * `PasswordChangeDto` to hold the new password data and adds it to the model.
     *
     * @param id    The ID of the user whose password is to be changed.
     * @param model The model to which user data and password change DTO will be added.
     * @return The name of the Thymeleaf template for changing the user's password.
     */
    @GetMapping("/changePasswordByAdmin/{id}")
    public String changePasswordByAdmin(@PathVariable Long id,  Model model) {

        User currentUser = userService.getUser(id);
        model.addAttribute("user",currentUser);

        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        model.addAttribute("passwordChangeDto",passwordChangeDto);

        return "user/changePasswordByAdmin";
    }


    /**
     * Handles the POST request for changing a user's password by an admin.
     * This method retrieves the user with the given ID and attempts to change
     * their password using the provided `PasswordChangeDto`. It adds the user
     * and either a success or error message to the model depending on the
     * outcome of the password change operation.
     *
     * @param id                The ID of the user whose password is to be changed.
     * @param passwordChangeDto The DTO containing the new password information.
     * @param model             The model to which user data and success/error messages will be added.
     * @return The name of the Thymeleaf template for changing the user's password.
     */
    @PostMapping("/changePasswordByAdmin/{id}")
    public String changePasswordByAdmin(@PathVariable Long id,
                                        @ModelAttribute PasswordChangeDto passwordChangeDto,
                                        Model model) {

        User currentUser = userService.getUser(id);

        boolean isSuccess = userService.changeUserPasswordByAdmin(currentUser.getUserName(), passwordChangeDto);

        model.addAttribute("user",currentUser);

        if (isSuccess) {
            model.addAttribute("message", "Password changed successfully.");
        } else {
            model.addAttribute("error", "Password change failed. Please try again.");
        }
        return "user/changePasswordByAdmin";
    }

}
