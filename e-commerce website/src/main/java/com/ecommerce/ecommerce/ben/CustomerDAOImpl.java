package com.ecommerce.ecommerce.ben;

/*
import com.ecommerce.ecommerce.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Customer findById(Integer id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> theQuery = entityManager.createQuery("from Customer", Customer.class);
        return theQuery.getResultList();
    }


    @Override
    public List<Customer> findByUserNameAndPass(String userName , String password) {

        // create query
        TypedQuery<Customer> theQuery = entityManager.createQuery(
                "from Customer where userName=:theUserName and password=:thePassword", Customer.class);

        // set the query parameters
        theQuery.setParameter("theUserName", userName);
        theQuery.setParameter("thePassword", password);

        //return query results
        return theQuery.getResultList();
    }






}

 */

