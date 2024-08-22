package com.ecommerce.ecommerce.security;

import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    /**
     * This constructor initializes the CustomAuthenticationSuccessHandler with a UserService.
     * The UserService is used to retrieve user details after successful authentication.
     *
     * @param theUserService The UserService used to interact with the user data.
     */
    public CustomAuthenticationSuccessHandler(UserService theUserService) {
        userService = theUserService;
    }

    /**
     * This method is invoked when a user successfully logs in. It retrieves the authenticated user's
     * username, fetches the corresponding User object from the database using the UserService, and
     * stores the User object in the session. After that, the user is redirected to the "itemSells" page.
     *
     * @param request The HttpServletRequest object for the current request.
     * @param response The HttpServletResponse object for the current response.
     * @param authentication The Authentication object containing details about the authenticated user.
     * @throws IOException If an input or output exception occurs.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("In customAuthenticationSuccessHandler");

        String userName = authentication.getName();

        System.out.println("userName=" + userName);

        User theUser = userService.findByUserName(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);

        // forward to home page
        response.sendRedirect(request.getContextPath() + "/itemSells");
    }

}


