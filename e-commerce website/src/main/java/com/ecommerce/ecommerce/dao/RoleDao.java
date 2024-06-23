package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

	public Long count();
	public void save(Role theRole);

}


