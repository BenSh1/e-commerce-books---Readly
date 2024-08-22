package com.ecommerce.ecommerce.ben;

/*
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails customer = User.withUsername("customer")
                .passwordEncoder(passwordEncoderFunction) // Pass the function reference
                .password("password")
                .roles("CUSTOMER")
                .build();

        UserDetails manager = User.withDefaultPasswordEncoder()
                .username("manager")
                .password("password")
                .roles("CUSTOMER", "MANAGER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("CUSTOMER", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(customer, manager, admin);

    }

 */



/*
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

 */

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
