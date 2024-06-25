package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

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

/*
    public Book findBookById(Long id) {
        return bookDao.findById(id).orElse(null);
    }

 */
}