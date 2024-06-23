package com.ecommerce.ecommerce.security;


import com.ecommerce.ecommerce.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeRequests(configurer ->
                        configurer
                                .requestMatchers("/","/static/**", "/images/**", "/register/**" , "/showMyLoginPage","/itemSells",
                                        "/addBook","/bookList", "/bookList/**","/bookList2").permitAll()  // Allow access to URLs starting with /public
                                //.requestMatchers("/home/**").hasRole("EMPLOYEE")
                                .requestMatchers("/home/**").hasRole("CUSTOMER")
                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                //.defaultSuccessUrl("/home", true) // Redirect to home page on successful login
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

    //  add support for JDBC... no more hardcoded users :-)
    /*
    The JdbcUserDetailsManager in Spring Security uses specific SQL queries to manage user details
    in the database. By default, it expects certain tables and columns to exist for users and authorities
    (roles/permissions). These tables and columns can be customized if your database schema does not match
    the defaults.
    Default Table and Column Names
    By default, JdbcUserDetailsManager uses the following tables and columns:

    Users Table:

    Table Name: users
    Columns: username, password, enabled
    Authorities Table:

    Table Name: authorities
    Columns: username, authority

     */
    /*
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select username, password, enabled from user where username=?");
        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select name " +
                        "from role " +
                        "INNER JOIN users_roles ON role.id = users_roles.role_id" +
                        "INNER JOIN user ON users_roles.user_id = user_id" +
                        "where username=?");
                        //"where  users_roles.user_id =username.id");

        return new JdbcUserDetailsManager(dataSource);
    }
    */


   /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();


        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }
    */
}

