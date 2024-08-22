package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.dto.PasswordChangeDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.service.UserService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

	final int SECOND_ITEM_IN_ROLES_LIST  = 1;
	final int THIRD_ITEM_IN_ROLES_LIST  = 2;



	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private final OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private CartItemsRepository cartItemsRepository;


	private BCryptPasswordEncoder passwordEncoder;


	/**
	 * Constructor for the UserServiceImpl class.
	 * It initializes the UserService implementation with the required dependencies, which are injected via constructor injection.
	 * This constructor uses the @Autowired annotation to automatically wire the UserDao, RoleDao, BCryptPasswordEncoder,
	 * and OrderDetailsRepository beans.
	 *
	 * @param userDao the DAO for accessing user data.
	 * @param roleDao the DAO for accessing role data.
	 * @param passwordEncoder the encoder for encoding user passwords.
	 * @param orderDetailsRepository the repository for accessing order details data.
	 */
	@Autowired
	public UserServiceImpl(UserDao userDao, RoleDao roleDao , BCryptPasswordEncoder passwordEncoder, OrderDetailsRepository orderDetailsRepository) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;
		this.orderDetailsRepository = orderDetailsRepository;
	}

	/**
	 * Saves a new user in the database.
	 * This function converts a WebUser object to a User entity, assigns the user a default role of "customer",
	 * encodes the user's password, and saves the user in the database. The function is transactional to ensure
	 * that the entire operation is completed successfully or rolled back in case of an error.
	 *
	 * @param webUser the web user data transfer object containing the user's details.
	 */
	@Transactional
	@Override
	public void save(WebUser webUser) {

		User user = new User();

		// assign user details to the user object
		user.setUserName(webUser.getUserName());
		user.setPassword(passwordEncoder.encode(webUser.getPassword()));
		user.setFirstName(webUser.getFirstName());
		user.setLastName(webUser.getLastName());
		user.setEmail(webUser.getEmail());
		user.setPhone(webUser.getPhone());
		user.setCountry(webUser.getCountry());
		user.setCity(webUser.getCity());
		user.setStreetAddress(webUser.getStreetAddress());
		user.setApartmentNumber(webUser.getApartmentNumber());
		user.setZipCode(webUser.getZipCode());
		user.setCreditCardNumber(webUser.getCreditCardNumber());
		user.setCreditCardCompany(webUser.getCreditCardCompany());
		user.setCardExpiryMonth(webUser.getCardExpiryMonth());
		user.setCardExpiryYear(webUser.getCardExpiryYear());

		user.setEnabled(true);

		// give user default role of "customer"
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));

		// save user in the database
		userDao.save(user);
	}

	/**
	 * Adds a new user to the database.
	 * This function directly saves the provided User entity to the database.
	 *
	 * @param user the user entity to be saved.
	 */
	@Override
	public void addUser(User user) {
		userDao.save(user);
	}

	/**
	 * This function checks the database for a user with the specified username
	 * and returns the corresponding User entity.
	 *
	 * @param userName the username of the user to be found.
	 * @return the user entity with the specified username, or null if not found.
	 */
	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	/**
	 * This function returns a list of all users currently stored in the database.
	 *
	 * @return a list of all users.
	 */
	@Override
	public List<User> getUsers() {
		List<User> users = userDao.findAll();

		return users;
	}

	/**
	 * This method retrieves a User object from the database based on the provided username.
	 * If no user is found, a UsernameNotFoundException is thrown. If the user is found,
	 * their roles are mapped to authorities and a UserDetails object is returned for authentication.
	 *
	 * @param userName The username of the user to be loaded.
	 * @return A UserDetails object containing the user's credentials and authorities.
	 * @throws UsernameNotFoundException If the username is not found in the database.
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				authorities);

	}

	/**
	 * This method converts a collection of Role objects into a collection of
	 * SimpleGrantedAuthority objects, which represent the roles and permissions
	 * granted to a user in Spring Security.
	 *
	 * @param roles The collection of Role objects to be mapped.
	 * @return A collection of SimpleGrantedAuthority objects representing the user's roles.
	 */
	private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role tempRole : roles) {
			SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
			authorities.add(tempAuthority);
		}

		return authorities;
	}

	/**
	 * This function searches the database for a user with the specified username
	 * and returns the corresponding User entity.
	 *
	 * @param userName the username of the user to be found.
	 * @return the User entity with the specified username, or null if not found.
	 */
	@Override
	public User findUserByName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	/**
	 * This function returns the User entity corresponding to the specified ID from the database.
	 *
	 * @param id the ID of the user to be retrieved.
	 * @return the User entity with the specified ID, or null if not found.
	 */
	@Override
	public User getUser(Long id) {
		return userDao.findById(id);
	}

	/**
	 * Updates a user's details and role.
	 * This function updates the details of an existing user in the database with new information provided.
	 * It also assigns a new role to the user based on the specified role name.
	 *
	 * @param id the ID of the user to be updated.
	 * @param theUser the new user details to be applied.
	 * @param role the new role to be assigned to the user.
	 */
	@Override
	public void update(Long id, User theUser , String role) {

		User existingUser = updateParametersForUser(id,theUser);

		Role newRole = roleDao.findRoleByName(role);

		existingUser.setRoles(new ArrayList<Role>());
		List<Role> userRoles = new ArrayList<>();

		List<Role> r = roleDao.getAllRoles();

		if(role.equals("ROLE_CUSTOMER") )
		{
			userRoles.add(r.getFirst());
			//existingUser.setRoles(userRoles);
		}
		else if(role.equals("ROLE_MANAGER") )
		{
			userRoles.add(r.getFirst());
			userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
			//existingUser.setRoles(userRoles);
		}
		else
		{
			userRoles.add(r.getFirst());
			userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
			userRoles.add(r.get(THIRD_ITEM_IN_ROLES_LIST));
			//existingUser.setRoles(userRoles);
		}
		existingUser.setRoles(userRoles);

		userDao.save(existingUser);
	}

	/**
	 * Updates a user's details in the database.
	 * This helper function is used to update the user entity with new information provided in the `theUser` object.
	 * It also assigns the user the same role they previously held.
	 *
	 * @param id the ID of the user to be updated.
	 * @param theUser the new user details to be applied.
	 * @return the updated User entity.
	 */
	public User updateParametersForUser(Long id,User theUser){

		User existingUser = userDao.findById(id);

		existingUser.setFirstName(theUser.getFirstName());
		existingUser.setLastName(theUser.getLastName());
		existingUser.setPhone(theUser.getPhone());
		existingUser.setCountry(theUser.getCountry());
		existingUser.setCity(theUser.getCity());
		existingUser.setStreetAddress(theUser.getStreetAddress());
		existingUser.setApartmentNumber(theUser.getApartmentNumber());
		existingUser.setZipCode(theUser.getZipCode());
		existingUser.setCreditCardNumber(theUser.getCreditCardNumber());
		existingUser.setCreditCardCompany(theUser.getCreditCardCompany());
		existingUser.setCardExpiryMonth(theUser.getCardExpiryMonth());
		existingUser.setCardExpiryYear(theUser.getCardExpiryYear());
		existingUser.setEnabled(true);

		return existingUser;
	}



	/**
	 * Updates a user's details.
	 * This function updates the details of an existing user in the database with the information provided in `theUser`.
	 * The user is assigned a default role of "customer".
	 *
	 * @param id the ID of the user to be updated.
	 * @param theUser the new user details to be applied.
	 */
	@Override
	public void update(Long id, User theUser) {

		User existingUser = userDao.findById(id);
		existingUser.setFirstName(theUser.getFirstName());
		existingUser.setLastName(theUser.getLastName());
		existingUser.setPhone(theUser.getPhone());
		existingUser.setCountry(theUser.getCountry());
		existingUser.setCity(theUser.getCity());
		existingUser.setStreetAddress(theUser.getStreetAddress());
		existingUser.setApartmentNumber(theUser.getApartmentNumber());
		existingUser.setZipCode(theUser.getZipCode());
		existingUser.setCreditCardNumber(theUser.getCreditCardNumber());
		existingUser.setCreditCardCompany(theUser.getCreditCardCompany());
		existingUser.setCardExpiryMonth(theUser.getCardExpiryMonth());
		existingUser.setCardExpiryYear(theUser.getCardExpiryYear());
		existingUser.setEnabled(true);

		existingUser.setRoles(new ArrayList<Role>());
		List<Role> userRoles = new ArrayList<>();
		List<Role> r = roleDao.getAllRoles();
		userRoles.add(r.getFirst());
		existingUser.setRoles(userRoles);

		userDao.save(existingUser);
	}


	/**
	 * This function removes a user and their associated data (cart items, orders, and roles) from the database.
	 * The operation is transactional to ensure that all related deletions are completed successfully or rolled back in case of an error.
	 *
	 * @param id the ID of the user to be deleted.
	 */
	@Override
	@Transactional
	public void deleteUser(Long id) {

		User existingUser = userDao.findById(id);

		cartItemsRepository.deleteByUser(existingUser);

		List<Order> orders = orderDao.findOrdersByIdOfCustomer(existingUser.getId());

		for(Order order : orders){
			orderDetailsRepository.deleteOrderDetailsByOrder(order);
			orderDao.deleteOrderById(order.getOrderId());
		}

		// Remove the associations in the user_role table
		existingUser.getRoles().clear();

		// Now delete the user
		userDao.deleteUserById(id);

	}

	/**
	 * Changes a user's password.
	 * This function updates the user's password after validating the current password and ensuring the new password matches the confirmation password.
	 *
	 * @param username the username of the user whose password is to be changed.
	 * @param passwordChangeDto the data transfer object containing the current password, new password, and confirmation password.
	 * @return true if the password was successfully changed, false otherwise.
	 */
	public boolean changeUserPassword(String username, PasswordChangeDto passwordChangeDto) {
		User user = userDao.findByUserName(username);

		// Check if current password matches
		if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), user.getPassword())) {
			return false; // Current password doesn't match
		}

		// Check if new password and confirm password match
		if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
			return false; // New password and confirm password don't match
		}

		// Encode and set the new password
		user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
		//userRepository.save(user);
		userDao.save(user);

		return true;
	}

	/**
	 * Allows an admin to change a user's password.
	 * This method checks if the new password and confirm password match.
	 * If they do, it encodes the new password and updates the user's password in the database.
	 *
	 * @param username The username of the user whose password is to be changed.
	 * @param passwordChangeDto A DTO containing the new password and its confirmation.
	 * @return true if the password was successfully changed, false otherwise.
	 */
	public boolean changeUserPasswordByAdmin(String username, PasswordChangeDto passwordChangeDto) {
		User user = userDao.findByUserName(username);

		// Check if new password and confirm password match
		if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getConfirmPassword())) {
			return false; // New password and confirm password don't match
		}

		// Encode and set the new password
		user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
		userDao.save(user);

		return true;
	}


	/**
	 * Searches for users based on a query string.
	 * This method retrieves a list of users whose username or other fields match
	 * the query string, ignoring case. It then filters out any users who are not enabled.
	 *
	 * @param query The search query string.
	 * @return A list of enabled users that match the search criteria.
	 */
	public List<User> searchUsers(String query) {

		// Assuming userDao has a method to find users by username or other fields
		List<User> users = userDao.findByUsernameContainingIgnoreCase(query);

		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (!user.isEnabled()) {
				iterator.remove(); // Safe removal
			}
		}
		return users;
	}


	/**
	 * Retrieves the current user from the session.
	 * This function returns the currently logged-in user from the HTTP session.
	 * If the user is not logged in, an exception is thrown.
	 *
	 * @param session the current HTTP session.
	 * @return the currently logged-in User entity.
	 * @throws RuntimeException if the user is not logged in.
	 */
	public User getCurrentUser(HttpSession session) {
		// Get the current user
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null) {
			throw new RuntimeException("User not logged in");
		}
		return currentUser;
	}

}








