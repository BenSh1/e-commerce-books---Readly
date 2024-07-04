package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;

import com.ecommerce.ecommerce.dao.CartItemsRepository;
import com.ecommerce.ecommerce.entity.Book;

import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
/*
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

 */
/*

    @Autowired
    private BookService bookService;
 */

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private BookDao bookDao;

    public List<CartItems> getCartForUser(User user) {
        return cartItemsRepository.findByUser(user);
    }

    public void addToCart(User user, Long bookId) {
        Book book = bookDao.findById(bookId);
        CartItems cartItems = cartItemsRepository.findByUserAndBook(user, book)
                .orElseGet(CartItems::new);
        cartItems.setUser(user);
        cartItems.setBook(book);
        cartItems.setQuantity(cartItems.getQuantity() + 1);
        cartItemsRepository.save(cartItems);
    }

    public void clearCart(User user) {
        List<CartItems> cartItems = cartItemsRepository.findByUser(user);
        cartItemsRepository.deleteAll(cartItems);
    }


/*
    public void addToCart(User user , Long bookId, int quantity) {

        //Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Book book = bookDao.findById(bookId);
        //CartItem cartItem = new CartItem();
        CartItem cartItem = cartItemsRepository.findByUserAndBook(user, book)
                .orElseGet(() -> new CartItem());
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(quantity*book.getPrice());

        cartItemsRepository.save(cartItem);

    }

 */
    /*
    public List<CartItem> getCartForUser(User user) {
        return cartItemsRepository.findByUser(user);
    }

     */

    /*
    public void clearCart() {
        cartItemRepository.deleteAll();
    }

     */

/*
    public Cart addToCart(Long cartId, Long bookId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());

        //Book book = bookDao.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Book book = bookDao.findById(bookId);
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cartItem.setPrice(quantity*book.getPrice());

        //cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);

        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

 */
/*
    public Cart addToCart2(Book book, int quantity) {

        Cart cart = cartRepository.findById(cartId).orElse(new Cart());

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cartItem.setPrice(quantity*book.getPrice());

        //cartItemRepository.save(cartItem);
        cart.getItems().add(cartItem);

        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

 */
/*
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

 */


}
