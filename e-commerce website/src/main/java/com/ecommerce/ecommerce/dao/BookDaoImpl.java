package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    private EntityManager entityManager;

    public BookDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Book theBook) {
        entityManager.persist(theBook);
    }

    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }


    @Transactional
    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book findBookByName(String theBookName) {
        // retrieve/read from database using name
        TypedQuery<Book> theQuery = entityManager.createQuery("from Book where title=:bookName", Book.class);
        theQuery.setParameter("bookName", theBookName);

        Book theBook = null;

        try {
            theBook = theQuery.getSingleResult();
        } catch (Exception e) {
            theBook = null;
        }

        return theBook;
    }
}
