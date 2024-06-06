package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="orderDetails")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderDetailID")
    private int orderDetailID;
    @Column(name="orderID")
    private int orderID;
    @Column(name="bookID")
    private int bookID;
    @Column(name="quantity")
    private int quantity;
    @Column(name="price")
    private int price;


    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
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

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailID=" + orderDetailID +
                ", orderID=" + orderID +
                ", bookID=" + bookID +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
