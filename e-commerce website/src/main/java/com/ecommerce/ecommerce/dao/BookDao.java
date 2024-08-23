package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Role;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void save(Book theBook);

    Book findById(Long id);
    List<Book> findAll();
    Book findBookByName(String theBookName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id = :bookId")
    Optional<Book> findByIdWithLock(@Param("bookId") Long bookId);

    List<Book> findByCategory(String category);
    List<String> findDistinctCategory();
    List<Book> findByTitleContainingIgnoreCase(String query);


    void deleteBookById(Long id);

    Long count();

}
