package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        //books.add(book);
        bookDao.save(book);
    }
/*
    public List<Book> getBooks() {
        return bookDao.findAll();
    }

 */
/*
    public List<Book> getBooks() {
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            book.setImageBase64(book.getImageBase64());
        }
        return books;
    }

 */

    public List<Book> getBooks() {
        List<Book> books = bookDao.findAll();
        /*
        for (Book book : books) {
            book.setImageBase64(book.getImageBase64());
        }

         */
        return books;
    }

    public Book getBook(Long id) {
        return bookDao.findById(id);
    }

    /*
    public List<Book> getBooks() {
        return books;
    }

     */

/*
    public Book findBookById(Long id) {
        return bookDao.findById(id).orElse(null);
    }

 */
}