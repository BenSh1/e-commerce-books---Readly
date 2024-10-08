package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.dto.WebUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);

	void save(WebUser webUser);

	void save(User user);


	void addUser(User user);

	List<User> getUsers();

	//List<WebUser> getUsers();

	User findUserByName(String userName);
	User getUser(Long id);

	void update(Long id, User theUser, String role);
	void update(Long id, User theUser);

	void deleteUser(Long id);

	List<User> searchUsers(String query);

		//User getCurrentlyLoggedInUser(Authentication authentication);
	//String getCurrentCustomerUsername();

	boolean changeUserPassword(String username, PasswordChangeDto passwordChangeDto);
	boolean changeUserPasswordByAdmin(String username, PasswordChangeDto passwordChangeDto);
	boolean isConfirmPasswordEqualToPassword(String username, String password);

	User getCurrentUser(HttpSession session);

	WebUser convertToWebUser(User user);
	User convertToUser(WebUser webUser, User user);





	}




