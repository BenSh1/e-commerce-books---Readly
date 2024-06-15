package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
