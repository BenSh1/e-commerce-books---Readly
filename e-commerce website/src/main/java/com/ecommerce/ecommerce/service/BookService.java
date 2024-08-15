package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.BookRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;
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

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book)  {
        bookDao.save(book);
    }

/*
    public void addBook(Book book, MultipartFile file) throws IOException {
        //books.add(book);
        if (file != null && !file.isEmpty()) {
            book.setImage(file.getBytes());
        }
        bookDao.save(book);
    }

    public byte[] getImageByBookId(Long bookId) {
        Book book = bookDao.findById(bookId);
        return book.getImage();
    }

 */

    public List<Book> getBooks() {

        return bookDao.findAll();
    }


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

    public Book getBook(Long id) {
        return bookDao.findById(id);
    }
    /*
    public Book findBookById(Long id) {
        return bookDao.findById(id).orElse(null);
    }

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

    @Transactional
    public void delete(Long id) {
        Book existingBook = bookDao.findById(id);

        if (existingBook != null) {
            // Mark for deletion
            //entityManager.remove(entity);
            bookDao.deleteBookById(id);
        }

    }

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

        //return bookRepository.findByTitleContainingIgnoreCase(query);
    }

    public boolean isBookAvailable(Long id){
        Book existingBook = bookDao.findById(id);
        return existingBook.getStock() != 0;
    }


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

    public List<String> getAllSubjects() {
        return bookRepository.findDistinctCategory();
    }

}