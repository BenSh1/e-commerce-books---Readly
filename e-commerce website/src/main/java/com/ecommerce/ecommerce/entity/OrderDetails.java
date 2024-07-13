package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="orderDetails")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int orderDetailID;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="price")
    private int price;



    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /*
    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @Column(name="order_id")
    private Order order;



    @Column(name="orderStatus")
    private String orderStatus;

    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "book_id")
    private Book book;


    // a user can have multiple cart items
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

 */


}
