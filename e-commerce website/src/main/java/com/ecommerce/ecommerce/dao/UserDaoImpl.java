package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        //entityManager.persist(theUser);

        // create the user ... finally LOL
        entityManager.merge(theUser);

    }
}




