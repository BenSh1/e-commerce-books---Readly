package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dao.BookDao;

import com.ecommerce.ecommerce.dao.CartItemsRepository;
import com.ecommerce.ecommerce.dao.UserDao;
import com.ecommerce.ecommerce.entity.Book;

import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import jakarta.servlet.http.HttpSession;
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

    @Transactional
    public void addToCartWithQuantity(User user, Long bookId, int quantity) {
        Book book = bookDao.findById(bookId);

        User currentUser = userDao.findByUserName(user.getUserName());

        CartItems cartItems = cartItemsRepository.findByUserAndBook(currentUser, book)
                .orElseGet(CartItems::new);

        cartItems.setUser(currentUser);
        cartItems.setBook(book);
        cartItems.setPrice(book.getPrice());
        cartItems.setQuantity(quantity);

        cartItemsRepository.save(cartItems);
    }


    public void removeBookFromCart(Integer id , User user) {
        System.out.println("----------------------------------id: "+ id );

        User currentUser = userDao.findByUserName(user.getUserName());
        System.out.println("userDao.findByUserName : " + currentUser.toString());


        List<CartItems> cartItemsList = this.getCartForUser(currentUser);
        for(CartItems tempItem : cartItemsList) {
            if(tempItem.getBook().getBookId() == id){
                //cartItemsList.remove(tempItem);
                cartItemsRepository.delete(tempItem);
            }
        }

        /*
        List<CartItems> cartItems = cartItemsRepository.find(id);
        cartItemsRepository.delete(cartItems);

         */

    }

    public void clearCart(User user) {
        List<CartItems> cartItems = cartItemsRepository.findByUser(user);
        cartItemsRepository.deleteAll(cartItems);
    }

    @Transactional
    public void updateQuantity(User user, Long bookId, int quantity) {
        Book book = bookDao.findById(bookId);

        if(book.getStock() - quantity >= 0 && quantity <= 3)
        {
            CartItems cartItem = cartItemsRepository.findByUserAndBookId(user, bookId);
            if (cartItem != null) {
                cartItem.setQuantity(quantity);
                cartItemsRepository.save(cartItem);
            }
        }

    }

}
