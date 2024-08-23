package com.ecommerce.ecommerce.dao;


import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {


    @Autowired
    private EntityManager entityManager;

    /**
     * Constructs a OrderDaoImpl instance with the given EntityManager.
     * This constructor initializes the OrderDaoImpl with the provided EntityManager to interact with the database.
     *
     * @param theEntityManager the EntityManager used to interact with the database.
     */
    public OrderDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    /**
     * Saves or updates an Order entity.
     * This method merges the state of the given Order entity into the current persistence context.
     * If the entity already exists, it updates it; otherwise, it creates a new one.
     *
     * @param theOrder the Order entity to be saved or updated.
     */
    @Override
    @Transactional
    public void save(Order theOrder ) {
        //entityManager.merge(theOrder);
        entityManager.persist(theOrder);
    }





    /**
     * Finds all Order entities associated with a specific customer ID.
     * This method retrieves orders from the database where the associated user's ID matches
     * the provided customer ID.
     *
     * @param theId the ID of the customer whose orders are to be retrieved.
     * @return a List of Order entities associated with the specified customer ID.
     */
    @Override
    public List<Order> findOrdersByIdOfCustomer(Long theId) {

        String hql = "FROM Order WHERE user.id = :userId";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userId", theId);

        List<Order> orders = query.getResultList();
        return orders;
    }


    @Override
    //public List<Order> findOrdersByUsername(String username) {
    public List<Order> findOrdersByUsername(Long theId) {

        String queryStr = "SELECT o FROM Order o JOIN FETCH o.orderDetails WHERE o.user.id = :userId";

        TypedQuery<Order> query = entityManager.createQuery(queryStr, Order.class);
        query.setParameter("userId", theId);

        return query.getResultList();
    }

    @Override
    public List<Order> findAll() {
        String queryStr = "SELECT o FROM Order o JOIN FETCH o.orderDetails";
        TypedQuery<Order> query = entityManager.createQuery(queryStr, Order.class);
        return query.getResultList();
    }


/*
    @Override
    public List<Order> findOrdersByIdOfBook(Long theId) {

        String hql = "FROM Order WHERE book.id = :bookId";
        Query query = entityManager.createQuery(hql);
        query.setParameter("bookId", theId);

        List<Order> orders = query.getResultList();
        return orders;
    }

 */

    /**
     * Deletes an Order entity by its ID.
     * This method removes the Order entity with the specified ID from the database.
     *
     * @param theId the ID of the order to be deleted.
     */
    @Override
    public void deleteOrderById(int theId) {
        Order order = entityManager.find(Order.class, theId);
        entityManager.remove(order);
    }


    /**
     * Finds an Order entity by its ID.
     * This method retrieves an Order entity from the database with the specified ID.
     *
     * @param id the ID of the order to be retrieved.
     * @return the Order entity with the specified ID, or null if not found.
     */
    public Order findOrderById(Long id) {
        return entityManager.find(Order.class, id);
    }


    /**
     * Finds all OrderDetails entities.
     * This method retrieves all OrderDetails entities from the database.
     *
     * @return a List of all OrderDetails entities.
     */
    @Override
    public List<OrderDetails> findAllOrderDetails(int theId) {

        // create query
        TypedQuery<OrderDetails> query = entityManager.createQuery(
                "from OrderDetails",OrderDetails.class);

        //execute query
        List<OrderDetails> orderDetails = query.getResultList();

        return orderDetails;
    }


    /**
     * Finds all OrderDetails entities associated with a specific order ID.
     * This method retrieves OrderDetails entities from the database where the associated order's ID matches
     * the provided order ID.
     *
     * @param theId the ID of the order whose details are to be retrieved.
     * @return a List of OrderDetails entities associated with the specified order ID.
     */
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
