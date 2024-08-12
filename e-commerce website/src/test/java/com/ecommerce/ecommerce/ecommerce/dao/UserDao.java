package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;

import java.util.List;


public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);

    List<User> findAll();
    //List<WebUser> findAll();

    User findById(Long id);
    //WebUser findById(Long id);

    void deleteUserById(Long id);

    public Long count();


}


