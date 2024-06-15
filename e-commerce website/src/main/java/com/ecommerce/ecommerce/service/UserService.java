package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);

}




