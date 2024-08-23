package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	/**
	 * Constructs a RoleDaoImpl instance with the given EntityManager.
	 * This constructor initializes the RoleDaoImpl with the provided EntityManager to interact with the database.
	 *
	 * @param theEntityManager the EntityManager used to interact with the database.
	 */
	public RoleDaoImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	/**
	 * Finds a Role entity by its name.
	 * This method retrieves a Role entity from the database where the role's name matches
	 * the provided role name.
	 *
	 * @param theRoleName the name of the role to be retrieved.
	 * @return the Role entity with the specified name, or null if no such role is found.
	 */
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

	/**
	 * Counts the total number of Role entities in the database.
	 * This method executes a query to count all Role entities and returns the total count.
	 *
	 * @return the total number of Role entities in the database.
	 */
	@Override
	public Long count() {
		String query = "SELECT COUNT(m) FROM Role m";
		Query countQuery = entityManager.createQuery(query);
		//TypedQuery<Long> theQuery = entityManager.createQuery(query, Long.class);
		Long totalEntities = (Long) countQuery.getSingleResult();
		System.out.println("Total entities in Role Table: " + totalEntities);

		return totalEntities;
	}

	/**
	 * Saves a Role entity to the database.
	 * This method persists the given Role entity into the database. If the entity already exists,
	 * it updates it; otherwise, it creates a new one.
	 *
	 * @param theRole the Role entity to be saved.
	 */
	@Override
	@Transactional
	public void save(Role theRole) {
		entityManager.persist(theRole);
	}

	/**
	 * Retrieves all Role entities from the database.
	 * This method executes a query to retrieve all Role entities and returns them as a List.
	 *
	 * @return a List of all Role entities in the database.
	 */
	@Override
	public List<Role> getAllRoles() {
		String query = "SELECT e FROM Role e";
		TypedQuery<Role> typedQuery = entityManager.createQuery(query, Role.class);
		return typedQuery.getResultList();
	}


}




