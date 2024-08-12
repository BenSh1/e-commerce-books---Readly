package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.dao.UserDaoImpl;
import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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

    private Logger logger = Logger.getLogger(getClass().getName());



    @Autowired
    private UserService userService;
    /*
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

     */
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

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

    private User getCurrentUser(HttpSession session) {
        // Get the current user
        User currentUser = (User) session.getAttribute("user");
        //User user = userService.get
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        return currentUser;
    }

    @GetMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable Long id,
                               /*@Valid @ModelAttribute("user") User theUser,*/
                               Model model,
                               HttpSession session) {
        //WebUser webUser = userService.getCustomer(id);
        System.out.println("--------------------------------------------------------");
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        //model.addAttribute("user", user);

        User currentUser = getCurrentUser(session);
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

        return "editCustomer";
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

    @PostMapping("/updateRankToCustomer/{id}")
    public String updateRankToCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleDao.getAllRoles();

        userRoles.add(r.getFirst());
        theUser.setRoles(userRoles);

        userDao.save(theUser);
        return "redirect:/customersList";

    }


    @PostMapping("/updateRankToManager/{id}")
    public String updateRankToManager(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleDao.getAllRoles();

        userRoles.add(r.getFirst());
        userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
        theUser.setRoles(userRoles);
        userDao.save(theUser);
        return "redirect:/customersList";

    }

    @PostMapping("/updateRankToAdmin/{id}")
    public String updateRankToAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        User theUser = userService.getUser(id);

        List<Role> userRoles = new ArrayList<>();

        List<Role> r = roleDao.getAllRoles();

        userRoles.add(r.getFirst());
        userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
        userRoles.add(r.get(THIRD_ITEM_IN_ROLES_LIST));

        theUser.setRoles(userRoles);
        userDao.save(theUser);
        return "redirect:/customersList";

    }




    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/customersList";
    }


    @GetMapping("/changePassword")
    public String changePassword(  Model model) {

        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        model.addAttribute("passwordChangeDto",passwordChangeDto);

        return "changePassword";
    }


    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute PasswordChangeDto passwordChangeDto, Principal principal, Model model) {
        String username = principal.getName();
        boolean success = userService.changeUserPassword(username, passwordChangeDto);
        System.out.println("=======in changePassword=======================");
        System.out.println("username : " + username);

        if (success) {
            model.addAttribute("message", "Password changed successfully.");
            System.out.println("==========successfully============");
        } else {
            model.addAttribute("error", "Password change failed. Please try again.");
            System.out.println("============failed============");

        }

        return "changePassword";
    }
/*
    @GetMapping("/forgetPassword")
    public String getForgotPassword() {

        return "forgetPassword";
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
        return "forgotPassword";
    }

 */



}
