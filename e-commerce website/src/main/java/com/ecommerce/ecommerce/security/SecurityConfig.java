package com.ecommerce.ecommerce.security;

import com.ecommerce.ecommerce.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines a bean for BCryptPasswordEncoder.
     *
     * This bean provides an instance of BCryptPasswordEncoder, which is used
     * to encode passwords securely using the BCrypt hashing algorithm.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This bean sets up a DaoAuthenticationProvider that uses a custom UserService
     * to retrieve user details and a BCryptPasswordEncoder to encode passwords.
     * It is responsible for handling the authentication process using the specified user service and password encoder.
     *
     * @param userService The custom user details service that provides user data.
     * @return A configured DaoAuthenticationProvider instance.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt

        return auth;
    }

    /**
     * Configures the security filter chain for the application.
     * This bean defines the security settings, including which URLs are publicly accessible
     * and which require authentication. It also sets up the login page, logout behavior,
     * and handles access-denied scenarios. The custom authentication success handler is used
     * to manage redirection upon successful login.
     *
     * @param http The HttpSecurity object used to configure security settings.
     * @param customAuthenticationSuccessHandler A custom handler to manage successful authentication.
     * @return A configured SecurityFilterChain instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/","/images/**" ,"/register/**"
                                        , "/showMyLoginPage","/itemSells"
                                        ,"/bookDetails/**","/contact").permitAll()  // Allow access to URLs starting with /public

                                .requestMatchers("/cart" ,"/myOrderList",
                                        "/editCustomer/**","/changePassword").hasRole("CUSTOMER")

                                .requestMatchers( "/addBook","/bookList"
                                        , "/orderList", "/editBook/**"
                                        ,"/menuForManager").hasRole("MANAGER")

                                .requestMatchers( "/changePasswordByAdmin/**","/customersList").hasRole("ADMIN")

                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                // automatically handles the authentication process
                                .loginProcessingUrl("/authenticateTheUser")
                                // allows you to define custom logic for what should happen when a user successfully logs in
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                                .logoutSuccessUrl("/")
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );

        return http.build();

    }

    /**
     * This bean provides an instance of BCryptPasswordEncoder, which can be used
     * to encode passwords securely. It is similar to the passwordEncoder() method
     * but returns a PasswordEncoder interface.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoderFunction() {
        return new BCryptPasswordEncoder();
    }
}

