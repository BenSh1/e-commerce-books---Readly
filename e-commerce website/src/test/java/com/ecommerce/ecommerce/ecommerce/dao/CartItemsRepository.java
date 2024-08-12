package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {
    List<CartItems> findByUser(User user);

    Optional<CartItems> findByUserAndBook(User user, Book book);
    CartItems findByUserAndBookId(User user, Long bookId);


}
