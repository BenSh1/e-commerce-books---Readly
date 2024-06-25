package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.RoleDao;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

	final int SECOND_ITEM_IN_ROLES_LIST  = 1;
	final int THIRD_ITEM_IN_ROLES_LIST  = 2;

	private UserDao userDao;

	private RoleDao roleDao;

    private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, RoleDao roleDao , BCryptPasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;

	}
	@Override
	public void save(WebUser webUser) {
		User user = new User();

		// assign user details to the user object
		user.setUserName(webUser.getUserName());
		user.setPassword(passwordEncoder.encode(webUser.getPassword()));
		user.setFirstName(webUser.getFirstName());
		user.setLastName(webUser.getLastName());
		user.setEmail(webUser.getEmail());
		user.setEnabled(true);

		// give user default role of "employee"
		//user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));

		// save user in the database
		userDao.save(user);
	}

	@Override
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	public List<User> getUsers() {
		List<User> users = userDao.findAll();

		return users;
	}

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
	private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role tempRole : roles) {
			SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
			authorities.add(tempAuthority);
		}

		return authorities;
	}


	@Override
	public User findUserByName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}


	@Override
	public User getUser(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void update(Long id, User theUser , String role) {

		User existingUser = userDao.findById(id);

		existingUser.setFirstName(theUser.getFirstName());
		existingUser.setLastName(theUser.getLastName());
		/*
		// give user default role of "customer"
		existingUser.setRoles(Arrays.asList(roleDao.findRoleByName(role)));

 		*/
		System.out.println("========================role=================== : "+ role);

		Role newRole = roleDao.findRoleByName(role);
		System.out.println("========================newRole=================== : "+ newRole);

		System.out.println("========================roleDAO.getALlRoles========= : "+ roleDao.getAllRoles());

		existingUser.setRoles(new ArrayList<Role>());
		List<Role> userRoles = new ArrayList<>();

		List<Role> r = roleDao.getAllRoles();

		if(role.equals("ROLE_CUSTOMER") )
		{
			userRoles.add(r.getFirst());
			existingUser.setRoles(userRoles);
		}
		else if(role.equals("ROLE_MANAGER") )
		{
			userRoles.add(r.getFirst());
			userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
			existingUser.setRoles(userRoles);
		}
		else
		{
			userRoles.add(r.getFirst());
			userRoles.add(r.get(SECOND_ITEM_IN_ROLES_LIST));
			userRoles.add(r.get(THIRD_ITEM_IN_ROLES_LIST));
			existingUser.setRoles(userRoles);
		}



		//List<Role> userRoles = existingUser.getRoles() != null ? new ArrayList<>(existingUser.getRoles()) : new ArrayList<>(); // Create a copy or new list
		//List<Role> userRoles =  new ArrayList<>(); // Create a copy or new list
/*
		Collection<Role> userRoles = existingUser.getRoles();
		userRoles.contains({2,"ROLE_MANAGER"});

 */
/*
		List<Role> userRoles = new ArrayList<>();

		if(role.equals("ROLE_CUSTOMER") )
		{
			existingUser.setRoles(new ArrayList<Role>() );
		}
		else{
			existingUser.setRoles(new ArrayList<Role>() );
			if(role.equals("ROLE_MANAGER")){
				userRoles.add()
			}


			if(role.equals("ROLE_ADMIN")){
				Collection<Role> collection = existingUser.getRoles();
				for(Role tempRole : collection)
				{
					if(tempRole.equals("ROLE_MANAGER"))
					{

					}
				}
			}
			userRoles = new ArrayList<>(existingUser.getRoles());
		}
		System.out.println("=======================checking========================= ");

		userRoles.add(newRole);
		existingUser.setRoles(userRoles);

*/


/*
		Role newRole = roleDao.findRoleByName(role);
		List<Role> userRoles = existingUser.getRoles() != null ? new ArrayList<>(existingUser.getRoles()) : new ArrayList<>(); // Create a copy or new list
		userRoles.add(newRole);
		existingUser.setRoles(userRoles);

 */


		userDao.save(existingUser);
	}
/*
	@Override
	public void save(WebUser webUser) {
		User user = new User();

		// assign user details to the user object
		user.setUserName(webUser.getUserName());
		user.setPassword(passwordEncoder.encode(webUser.getPassword()));
		user.setFirstName(webUser.getFirstName());
		user.setLastName(webUser.getLastName());
		user.setEmail(webUser.getEmail());
		user.setEnabled(true);


		// save user in the database
		userDao.save(user);
	}

 */

	@Override
	@Transactional
	public void deleteUser(Long id) {
		User existingUser = userDao.findById(id);

		// Remove the associations in the user_role table
		existingUser.getRoles().clear();

		// Now delete the user
		userDao.deleteUserById(id);
/*
		if (existingUser != null) {
			// Mark for deletion
			//entityManager.remove(entity);
			userDao.deleteUserById(id);
		}

 */


	}








	/*
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

	 */


}








