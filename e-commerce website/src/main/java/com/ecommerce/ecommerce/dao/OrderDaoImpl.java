package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.OrderDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {


    @Autowired
    private EntityManager entityManager;

    public OrderDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
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
