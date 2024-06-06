package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/*
@Repository
public class CustomerDAOImpl implements CustomerDAO{

    // define field for entity manager
    private EntityManager entityManager;

    // inject entity manager using constructor injection
    @Autowired
    public CustomerDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // inject entity manager using constructor injection
    @Override
    @Transactional
    public void save(Customer theCustomer) {
        entityManager.persist(theCustomer);

    }


}
*/
