package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.User;


public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
}
