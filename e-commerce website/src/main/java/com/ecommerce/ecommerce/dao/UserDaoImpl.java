package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    /**
     * Constructs a UserDaoImpl instance with the given EntityManager.
     * This constructor initializes the UserDaoImpl with the provided EntityManager to interact with the database.
     *
     * @param entityManager the EntityManager used to interact with the database.
     */
    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Finds a User entity by its username.
     * This method retrieves a User entity from the database where the username matches
     * the provided username and the user is enabled.
     *
     * @param theUserName the username of the user to be retrieved.
     * @return the User entity with the specified username and enabled status, or null if no such user is found.
     */
    @Override
    public User findByUserName(String theUserName) {

        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true", User.class);
        theQuery.setParameter("uName", theUserName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }
        return theUser;
    }

    @Override
    public User findByEmail(String email) {
        // retrieve/read from database using username
        TypedQuery<User> theQuery = entityManager.createQuery("from User where email=:email and enabled=true", User.class);
        theQuery.setParameter("email", email);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }
        return theUser;
    }

    /**
     * Saves or updates a User entity in the database.
     * This method merges the given User entity into the database.
     * If the entity already exists, it updates it; otherwise, it creates a new one.
     *
     * @param theUser the User entity to be saved or updated.
     */
    @Override
    @Transactional
    public void save(User theUser) {
        // create the user
        entityManager.merge(theUser);

    }

    /**
     * Retrieves all User entities from the database.
     * This method executes a JPQL query to retrieve all User entities and returns them as a List.
     *
     * @return a List of all User entities in the database.
     */
    @Override
    public List<User> findAll() {
        // Create JPQL query
        String query = "SELECT u FROM User u";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);

        // Execute the query and return results
        return typedQuery.getResultList();
    }

    /**
     * Finds a User entity by its ID.
     * This method retrieves a User entity from the database using the specified ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the User entity with the specified ID, or null if no such user is found.
     */
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Deletes a User entity from the database by its ID.
     * This method removes the User entity with the specified ID from the database.
     *
     * @param id the ID of the user to be deleted.
     */
    @Override
    public void deleteUserById(Long id) {
        User theUser = entityManager.find(User.class, id);
        entityManager.remove(theUser);
    }

    /**
     * Counts the total number of User entities in the database.
     * This method executes a query to count all User entities and returns the total count.
     *
     * @return the total number of User entities in the database.
     */
    @Override
    public Long count() {
        String query = "SELECT COUNT(m) FROM User m";
        Query countQuery = entityManager.createQuery(query);
        //TypedQuery<Long> theQuery = entityManager.createQuery(query, Long.class);
        Long totalEntities = (Long) countQuery.getSingleResult();
        System.out.println("Total entities in MyTable: " + totalEntities);

        return totalEntities;
    }

    /**
     * Finds and returns a list of users whose usernames contain the specified
     * search string, ignoring case sensitivity. This method utilizes JPQL
     * to perform a case-insensitive search for partial matches within the username field.
     *
     * @param username The search string to look for within usernames. The search is case-insensitive.
     * @return A list of users whose usernames contain the specified search string, ignoring case.
     */
    @Override
    public List<User> findByUsernameContainingIgnoreCase(String username) {

        String queryStr = "SELECT u FROM User u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :username, '%'))";
        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
        query.setParameter("username", username);

        return query.getResultList();
    }

}




