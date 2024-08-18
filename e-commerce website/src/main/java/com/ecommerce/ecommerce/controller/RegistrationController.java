package com.ecommerce.ecommerce.controller;



import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

    /**
     * This method is called automatically whenever the controller receives web requests.
     * The @InitBinder annotation tells Spring to register a custom editor (StringTrimmerEditor)
     * for all String fields in the model. The StringTrimmerEditor will trim leading and trailing
     * whitespace from input Strings. If the input is empty after trimming, it will be converted to null.
     */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

    /**
     * Displays the registration form to the user.
     * This method is mapped to the "/showRegistrationForm" URL and is triggered by a GET request.
     * It adds a new WebUser object to the model, which will be used to bind form data.
     * The registration form view is then returned.
     */
    @GetMapping("/showRegistrationForm")
    public String showMyRegisterPage(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel)
    {

        theModel.addAttribute("webUser", new WebUser());

        return "register/registration-form";
    }


    /**
     * Processes the registration form submitted by the user.
     * This method is mapped to the "/processRegistrationForm" URL and is triggered by a POST request.
     * It validates the form data, checks if the username already exists in the database, and
     * creates a new user account if validation passes. If there are errors, the registration
     * form is redisplayed with appropriate error messages.
     */
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel)
    {
        // Handle form data
        String userName = theWebUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "The user isn't available");

            logger.warning("User name already exists.");
            return "register/registration-form";
        }

        // create user account and store in the database
        userService.save(theWebUser);

        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", theWebUser);

        return "register/registration-confirmation";
    }

}


