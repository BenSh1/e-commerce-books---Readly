package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.dao.CartItemRepository;
import com.ecommerce.ecommerce.dao.CartRepository;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.Cart;
import com.ecommerce.ecommerce.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
/*
    @Autowired
    private BookRepository bookRepository;

 */
    @Autowired
    private BookDao bookDao;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart addToCart(Long cartId, Long bookId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        //Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Book book = bookDao.findById(bookId);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);

        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }


}
