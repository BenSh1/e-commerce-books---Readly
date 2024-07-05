package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;

import com.ecommerce.ecommerce.dao.CartItemsRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;

import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private UserDao userDao;

    public List<CartItems> getCartForUser(User user) {
        return cartItemsRepository.findByUser(user);
    }

    @Transactional
    public void addToCart(User user, Long bookId) {
        int isExistTheBookId = 0;
        System.out.println("in addToCart");
        System.out.println("user : " + user.toString());

        Book book = bookDao.findById(bookId);
        System.out.println("the book : " + book.toString());

        User currentUser = userDao.findByUserName(user.getUserName());
        System.out.println("userDao.findByUserName : " + currentUser.toString());

        List<CartItems> cartItemsList = this.getCartForUser(currentUser);

        //List<Book> booksOfUserInCart = new ArrayList<Book>();

/*
        if(cartItemsList.contains(cartItems)){

        }

 */


        for(CartItems tempItem : cartItemsList) {
            if(tempItem.getBook().getBookId() == book.getBookId())
            {
                isExistTheBookId = 1;
                break;
            }
        }
        if(isExistTheBookId == 1)
        {
            System.out.println("------------------------------------------------");
            System.out.println("the book is already exist in the cart!");
        }
        else{
            CartItems cartItems = cartItemsRepository.findByUserAndBook(currentUser, book)
                    .orElseGet(CartItems::new);

            cartItems.setUser(currentUser);
            cartItems.setBook(book);
            cartItems.setPrice(book.getPrice());
            cartItems.setQuantity(cartItems.getQuantity() + 1);

            System.out.println("the cartItems : " + cartItems.toString());

            cartItemsRepository.save(cartItems);
        }

    }

    public void removeBookFromCart(Integer id) {
        /*
        List<CartItems> cartItems = cartItemsRepository.find(id);
        cartItemsRepository.delete(cartItems);

         */
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
