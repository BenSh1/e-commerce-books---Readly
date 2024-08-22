package com.ecommerce.ecommerce.ben;
/*
import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;
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

	boolean changeUserPassword(String username, PasswordChangeDto passwordChangeDto);

}

 */




/*
	public String getCurrentCustomerUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			System.out.println("userDetails.getUsername() : " + userDetails.getUsername());
			return userDetails.getUsername();
		}
		return null;
	}

 */


/*
	@Override
	public User getCurrentlyLoggedInUser(Authentication authentication) {
		if(authentication == null)
			return null;

		User user = null;
		Object principal = authentication.getPrincipal();

		if(principal instanceof UserDetails){
			user = ((User) principal);
		}
		else if(principal instanceof ){}
	}

 */








	/*
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

	 */







