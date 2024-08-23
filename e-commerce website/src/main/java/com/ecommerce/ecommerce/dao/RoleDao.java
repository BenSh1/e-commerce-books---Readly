package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;

import java.util.List;

public interface RoleDao {

	Role findRoleByName(String theRoleName);

	Long count();

	void save(Role theRole);

	List<Role> getAllRoles();

}


