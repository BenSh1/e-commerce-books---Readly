package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.*;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    private final List<Book> books = new ArrayList<>();



    /**
     * This function adds a new book to the database.
     * It saves the provided book object using the `bookDao`.
     *
     * @param book the book object to be added to the database.
     */
    public void addBook(Book book)  {
        bookDao.save(book);
    }

    /**
     * This function retrieves all books from the database.
     * It returns a list of all book objects found.
     *
     * @return a list of all books in the database.
     */
    public List<Book> getBooks() {
        return bookDao.findAll();
    }


    /**
     * This function retrieves a list of all books from the database
     * and filters out any books that are not marked as "active".
     * It iterates over the list of books, and if a book's status
     * is not "active", it safely removes that book from the list.
     * The final list, containing only active books
     *
     * @return a list of active books.
     */
    public List<Book> getBooksExceptInactive() {
        List<Book> books = bookDao.findAll();

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (!book.getIsActive().equals("active")) {
                iterator.remove(); // Safe removal
            }
        }

        return books;
    }

    /**
     * This function retrieves a book from the database based on its unique ID.
     *
     * @param id the ID of the book to retrieve.
     * @return the book with the specified ID, or null if not found.
     */
    public Book getBook(Long id) {
        return bookDao.findById(id);
    }

    /**
     * This function updates an existing book's details in the database.
     * It retrieves the book by its ID, updates its fields with new values,
     * and then saves the updated book back to the database.
     * The function is transactional to ensure atomicity.
     *
     * @param id the ID of the book to update.
     * @param book the book object containing the new details.
     */
    @Transactional
    public void update(Long id, Book book) {
        //Book existingBook = entityManager.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        Book existingBook = bookDao.findById(id);

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());
        existingBook.setDescription(book.getDescription());
        existingBook.setStock(book.getStock());
        existingBook.setPrice(book.getPrice());
        existingBook.setImage(book.getImage());

        bookDao.save(existingBook);
    }

    /**
     * This function deletes a book from the database based on its ID.
     * It first deletes any associated cart items and order details related
     * to the book, then deletes the book itself. The function is transactional
     * to ensure that all operations complete successfully or none at all.
     *
     * @param id the ID of the book to delete.
     */
    @Transactional
    public void delete(Long id) {
        Book existingBook = bookDao.findById(id);

        cartItemsRepository.deleteByBook(existingBook);

        //List<Order> orders = orderDao.findOrdersByIdOfBook(((long)existingBook.getBookId()));

        List<OrderDetails> orderDetails = orderDetailsRepository.findOrderDetailsByBook(existingBook);

        List<Order> orders = new ArrayList<>();
        for(OrderDetails o : orderDetails){
            if(!orders.contains(o.getOrder()))
            {
                orders.add(o.getOrder());
            }
            //Order temp = o.getOrder();
            orderDetailsRepository.deleteDistinctByOrderDetailID(o.getOrderDetailID());
            //orderDao.deleteOrderById(temp.getOrderId());
        }
        for(Order o : orders){
            orderDao.deleteOrderById(o.getOrderId());
        }

        if (existingBook != null) {
            // Mark for deletion
            //entityManager.remove(entity);
            bookDao.deleteBookById(id);
        }

    }

    /**
     * This function searches for books in the database whose titles contain
     * a specified query string, ignoring case. It then filters out any books
     * that are not marked as "active" and returns the list of matching, active books.
     *
     * @param query the search query string.
     * @return a list of books whose titles match the query and are active.
     */
    public List<Book> searchBooks(String query) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(query);

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (!book.getIsActive().equals("active")) {
                iterator.remove(); // Safe removal
            }
        }
        return books;
    }

    /**
     * This function checks if a specific book is available by verifying its stock.
     * It retrieves the book by its ID and returns true if the stock is greater than zero.
     *
     * @param id the ID of the book to check.
     * @return true if the book is available (stock > 0), false otherwise.
     */
    public boolean isBookAvailable(Long id){
        Book existingBook = bookDao.findById(id);
        return existingBook.getStock() != 0;
    }

    /**
     * This function retrieves all books from the database that belong to a specific category.
     * It filters out any books that are not marked as "active" and returns the list of matching, active books.
     *
     * @param subject the category or subject of the books to retrieve.
     * @return a list of active books in the specified category.
     */
    public List<Book> getBooksBySubject(String subject) {
        List<Book> allBooksRelatedToSubject = bookRepository.findByCategory(subject);

        Iterator<Book> iterator = allBooksRelatedToSubject.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (!book.getIsActive().equals("active")) {
                iterator.remove(); // Safe removal
            }
        }
        //return bookRepository.findByCategory(subject);

        return allBooksRelatedToSubject;

    }

    /**
     * This function retrieves a list of all distinct categories (subjects) of books available in the database.
     *
     * @return a list of distinct book categories.
     */
    public List<String> getAllSubjects() {
        return bookRepository.findDistinctCategory();
    }

}