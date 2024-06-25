package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;

import java.util.Collection;
import java.util.List;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);

	public Long count();
	public void save(Role theRole);
	List<Role> getAllRoles();


}


