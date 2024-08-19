package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.user.WebUser;
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
    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


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
    @Transactional
    public void save(User theUser) {
        // create the user
        entityManager.merge(theUser);

    }

    @Override
    public List<User> findAll() {
        // Create JPQL query
        String query = "SELECT u FROM User u";
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);

        // Execute the query and return results
        return typedQuery.getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUserById(Long id) {
        User theUser = entityManager.find(User.class, id);
        entityManager.remove(theUser);
    }

    @Override
    public Long count() {
        String query = "SELECT COUNT(m) FROM User m";
        Query countQuery = entityManager.createQuery(query);
        //TypedQuery<Long> theQuery = entityManager.createQuery(query, Long.class);
        Long totalEntities = (Long) countQuery.getSingleResult();
        System.out.println("Total entities in MyTable: " + totalEntities);

        return totalEntities;
    }

}




