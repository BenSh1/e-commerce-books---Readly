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
    public void save(Book theBook);
    public Book findById(Long id);

    public List<Book> findAll();
    public void deleteBookById(Long id);
    public Long count();

    public Book findBookByName(String theBookName);
    //void update(Long id, Book book);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id = :bookId")
    Optional<Book> findByIdWithLock(@Param("bookId") Long bookId);


}
