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
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    /**
     * This function retrieves the cart items associated with a specific user.
     * It returns a list of all cart items belonging to the given user.
     *
     * @param user the user whose cart items are to be retrieved.
     * @return a list of cart items for the specified user.
     */
    public List<CartItems> getCartForUser(User user) {
        return cartItemsRepository.findByUser(user);
    }


    /**
     * This function adds a book to a user's cart. it's creates a new cart item with a quantity of 1 and adds it to the cart.
     * The function is transactional to ensure the operation's atomicity.
     *
     * @param user the user who is adding the book to their cart.
     * @param bookId the ID of the book to add to the cart.
     */
    @Transactional
    public void addToCart(User user, Long bookId) {

        Book book = bookDao.findById(bookId);

        User currentUser = userDao.findByUserName(user.getUserName());

        CartItems cartItems = cartItemsRepository.findByUserAndBook(currentUser, book)
                .orElseGet(CartItems::new);

        cartItems.setUser(currentUser);
        cartItems.setBook(book);
        cartItems.setPrice(book.getPrice());
        cartItems.setQuantity(cartItems.getQuantity() + 1);

        cartItemsRepository.save(cartItems);

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

    /**
     * This function removes a specific book from a user's cart.
     * It finds the cart item that matches the book ID and user, and then deletes it from the cart.
     *
     * @param id the ID of the book to remove from the cart.
     * @param user the user whose cart item is to be removed.
     */
    public void removeBookFromCart(Integer id , User user) {

        User currentUser = userDao.findByUserName(user.getUserName());

        List<CartItems> cartItemsList = this.getCartForUser(currentUser);
        for(CartItems tempItem : cartItemsList) {
            if(tempItem.getBook().getBookId() == id){
                cartItemsRepository.delete(tempItem);
            }
        }
    }
    /**
     * This function clears all items from a user's cart.
     * It retrieves all cart items for the user and deletes them from the database.
     *
     * @param user the user whose cart is to be cleared.
     */
    public void clearCart(User user) {
        List<CartItems> cartItems = cartItemsRepository.findByUser(user);
        cartItemsRepository.deleteAll(cartItems);
    }


    /**
     * This function updates the quantity of a specific book in a user's cart.
     * It checks if the requested quantity is within the allowed limits (stock >= quantity and quantity <= 3).
     * If valid, the cart item is updated with the new quantity and saved to the database.
     * The function is transactional to ensure the operation's atomicity.
     *
     * @param user the user whose cart item's quantity is to be updated.
     * @param bookId the ID of the book whose quantity is to be updated.
     * @param quantity the new quantity to set for the book in the cart.
     */
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
