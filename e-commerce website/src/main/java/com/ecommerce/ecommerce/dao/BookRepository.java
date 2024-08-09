package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByCategory(String subject);

    @Query("SELECT DISTINCT b.category FROM Book b")
    List<String> findDistinctCategory();
}




