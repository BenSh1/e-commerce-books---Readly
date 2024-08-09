package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);

	void save(WebUser webUser);

	List<User> getUsers();
	//List<WebUser> getUsers();


	User findUserByName(String userName);
	User getUser(Long id);

	void update(Long id, User theUser, String role);
	public void update(Long id, User theUser);

	void deleteUser(Long id);

	//User getCurrentlyLoggedInUser(Authentication authentication);
	//String getCurrentCustomerUsername();


}




