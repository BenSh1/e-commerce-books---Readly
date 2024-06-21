package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}


