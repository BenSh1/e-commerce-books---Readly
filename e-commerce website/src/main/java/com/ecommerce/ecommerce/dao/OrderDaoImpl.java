package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import com.ecommerce.ecommerce.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {


    @Autowired
    private EntityManager entityManager;

    public OrderDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    @Transactional
    public void save(Order theOrder ) {
        //entityManager.persist(theUser);
        // create the user
        entityManager.merge(theOrder);

    }

    public Order findOrderById(Long id) {
        return entityManager.find(Order.class, id);
    }



    @Override
    public List<OrderDetails> findAllOrderDetails(int theId) {

        // create query
        TypedQuery<OrderDetails> query = entityManager.createQuery(
                "from OrderDetails",OrderDetails.class);

        //execute query
        List<OrderDetails> orderDetails = query.getResultList();

        return orderDetails;
    }



    @Override
    public List<OrderDetails> findDetailsByOrderId(int theId) {

        // create query
        TypedQuery<OrderDetails> query = entityManager.createQuery(
                "from OrderDetails where Order.id =: data",OrderDetails.class);
        query.setParameter("data",theId);

        //execute query
        List<OrderDetails> orderDetails = query.getResultList();

        return orderDetails;
    }

}
