package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.User;
import java.util.List;


public interface UserDao  {

    User findByUserName(String userName);

    User findByEmail(String email);

    void save(User theUser);

    List<User> findAll();
    User findById(Long id);
    List<User> findByUsernameContainingIgnoreCase(String username);

    void deleteUserById(Long id);

    public Long count();



}


