package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="cartItems")
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer cartItemsId;

/*
    @Column(name="bookId")
    private int bookId;

 */
/*
    @Column(name="customerId")
    private int customerId;

 */

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private int price;


    // it indicates that a single Cart entity can be associated with one Book entity
    // many cart could have the same book

    // a single cart item is related to a single user and a single book,
    // a book can be in multiple cart items.
    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
                                            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "book_id")
    private Book book;

    // a user can have multiple cart items
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;



    //private List<Book> books;

/*
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

 */
/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

 */



    //private List<CartItems> items; // List of items in the cart


    //private List<Book> books;

    // getters and setters
/*
    public void addBook(Book book) {
        this.books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

 */

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public int getCartItemsId() {
        return cartItemsId;
    }

    public void setCartItemsId(int cartItemsId) {
        this.cartItemsId = cartItemsId;
    }

    public int getCartItemId() {
        return cartItemsId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemsId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
