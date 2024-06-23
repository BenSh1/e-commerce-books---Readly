package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	public RoleDaoImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Role findRoleByName(String theRoleName) {

		// retrieve/read from database using name
		TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:roleName", Role.class);
		theQuery.setParameter("roleName", theRoleName);
		
		Role theRole = null;
		
		try {
			theRole = theQuery.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}
		
		return theRole;
	}

	@Override
	public Long count() {
		String query = "SELECT COUNT(m) FROM Role m";
		Query countQuery = entityManager.createQuery(query);
		//TypedQuery<Long> theQuery = entityManager.createQuery(query, Long.class);
		Long totalEntities = (Long) countQuery.getSingleResult();
		System.out.println("Total entities in MyTable: " + totalEntities);

		return totalEntities;
	}

	@Override
	@Transactional
	public void save(Role theRole) {
		entityManager.persist(theRole);
	}


}




