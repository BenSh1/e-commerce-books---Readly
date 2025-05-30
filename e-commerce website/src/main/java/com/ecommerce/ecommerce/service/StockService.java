package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.exception.OutOfStockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final BookDao bookDao;
    // in-JVM lock map
    private final ConcurrentMap<Long, Lock> bookLocks = new ConcurrentHashMap<>();

    public StockService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /**
     * Reserve stock for a single book. Throws OutOfStockException if not enough.
     * Runs in its own transaction to cover the DB lock + update.
     */
    @Transactional
    public void reserveStock(Long bookId, int quantity) {
        Lock lock = bookLocks.computeIfAbsent(bookId, id -> new ReentrantLock());
        lock.lock();
        try {
            // assume findByIdWithLock() uses @Lock(PESSIMISTIC_WRITE)
            Book book = bookDao.findByIdWithLock(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            if (book.getStock() < quantity) {
                throw new OutOfStockException(book.getTitle());
            }

            book.setStock(book.getStock() - quantity);
            bookDao.save(book);
        } finally {
            lock.unlock();
            bookLocks.remove(bookId);
        }
    }


    /**
     * Return the titles of all books in the cart that can't be fulfilled.
     */
    @Transactional(readOnly = true)
    public List<String> findOutOfStockTitles(List<CartItems> cartItems) {
        return cartItems.stream()
                .map(ci -> {
                    Book book = bookDao.findById((long) ci.getBook().getBookId());
                    if (book == null) {
                        throw new RuntimeException("Book not found");
                    }

                    return (book.getStock() < ci.getQuantity())
                            ? book.getTitle()
                            : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
