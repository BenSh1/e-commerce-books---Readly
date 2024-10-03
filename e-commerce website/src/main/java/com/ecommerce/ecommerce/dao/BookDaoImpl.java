package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    private EntityManager entityManager;

    public BookDaoImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    /**
     * Saves or updates a Book entity.
     * This method merges the state of the given Book entity into the current persistence context.
     * If the entity already exists, it updates it; otherwise, it creates a new one.
     *
     * @param theBook the Book entity to be saved or updated.
     */
    @Override
    @Transactional
    public void save(Book theBook) {
        entityManager.merge(theBook);
    }

    /**
     * Retrieves all books from the database.
     * This method executes a JPQL query to retrieve all Book entities from the database and returns them as a list.
     *
     * @return a List of all Book entities.
     */
    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    /**
     * Finds a Book entity by its ID.
     * This method searches the database for a Book entity with the specified ID and returns it.
     *
     * @param id the ID of the book to be retrieved.
     * @return the Book entity with the specified ID, or null if not found.
     */
    @Transactional
    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }


    /**
     * Finds a Book entity by its title.
     * This method searches the database for a Book entity with the specified title and returns it.
     * If no book is found, it returns null.
     *
     * @param theBookName the title of the book to be retrieved.
     * @return the Book entity with the specified title, or null if not found.
     */
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

    /**
     * Deletes a Book entity by its ID.
     * This method removes the Book entity with the specified ID from the database.
     *
     * @param id the ID of the book to be deleted.
     */
    public void deleteBookById(Long id) {
        Book theBook = entityManager.find(Book.class, id);
        entityManager.remove(theBook);
    }

    /**
     *
     * Counts the total number of Book entities in the database.
     * This method executes a JPQL query to count all Book entities and returns the total count.
     *
     * @return the total number of Book entities in the database.
     */
    @Override
    public Long count() {
        String query = "SELECT COUNT(b) FROM Book b";
        Query countQuery = entityManager.createQuery(query);
        Long totalEntities = (Long) countQuery.getSingleResult();
        System.out.println("Total entities in Book Table: " + totalEntities);

        return totalEntities;
    }

    /**
     * Finds a Book entity by its ID with a pessimistic write lock.
     * This method retrieves a Book entity from the database with a pessimistic write lock,
     * preventing other transactions from modifying it until the current transaction completes.
     *
     * @param bookId the ID of the book to be retrieved.
     * @return an Optional containing the Book entity if found, or empty if not found.
     */
    @Override
    public Optional<Book> findByIdWithLock(Long bookId) {
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);
        return Optional.ofNullable(book);
    }

    /**
     * Finds and returns a list of books that belong to a specific category.
     * This method uses a JPQL (Java Persistence Query Language) query to select books from the database where
     * the category matches the specified parameter.
     *
     * @param category The category to filter books by.
     * @return A list of books that belong to the specified category.
     */
    @Override
    @Transactional
    public List<Book> findByCategory(String category) {
        // Create the JPQL query
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.category = :category", Book.class);
        // Set the parameter
        query.setParameter("category", category);

        // Execute the query and get the result list
        return query.getResultList();
    }

    /**
     * Finds and returns a list of distinct book categories.
     * This method uses a JPQL query to select unique categories from the Book entity.
     *
     * @return A list of distinct book categories.
     */
    @Override
    public List<String> findDistinctCategory() {

        // Create a JPQL query to select distinct categories
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT DISTINCT b.category FROM Book b", String.class);

        // Execute the query and return the result list
        return query.getResultList();
    }

    /**
     * Finds and returns a list of books whose titles contain the specified query string,
     * ignoring case sensitivity. This method uses a JPQL query with a case-insensitive
     * search to match book titles.
     *
     * @param query The search string to look for within book titles.
     * @return A list of books with titles containing the specified query string.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findByTitleContainingIgnoreCase(String query) {
        // Create a JPQL query to search for titles containing the query string, ignoring case
        TypedQuery<Book> typedQuery = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))", Book.class);

        // Set the parameter for the query
        typedQuery.setParameter("query", query);

        // Execute the query and return the result list
        return typedQuery.getResultList();
    }
}
