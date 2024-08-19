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
    private int cartItemsId;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private int price;


    // a single cart item is related to a single user and a single book

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



    /**
     * Getters + Setters
     *
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

    /**
     * Returns a string representation of the CartItems object.
     *
     * @return a string representation of the CartItems object.
     */
    @Override
    public String toString() {
        return "CartItems{" +
                "cartItemsId=" + cartItemsId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
