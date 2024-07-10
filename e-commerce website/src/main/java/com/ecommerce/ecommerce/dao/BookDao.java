package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;

import java.util.List;

public interface BookDao {
    public void save(Book theBook);
    public Book findBookByName(String theBookName);
    public Book findById(Long id);
    //public Book findAll();
    public List<Book> findAll();
    //void update(Long id, Book book);
    public void deleteBookById(Long id);
    public Long count();

}
