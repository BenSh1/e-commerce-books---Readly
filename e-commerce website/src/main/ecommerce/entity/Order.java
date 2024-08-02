package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int orderId;

    @Column(name="orderDate")
    private Date orderDate;

    @Column(name="totalAmount")
    private Double totalAmount;

    /*
    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @Column(name="user_id")
    private User user;

     */
/*
    @ManyToOne(fetch = FetchType.EAGER , cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private User user;

 */

    // a user can have multiple cart items
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<OrderDetails> orderDetails;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void add(OrderDetails tempOrderDetails) {

        if(orderDetails == null) {
            orderDetails = new ArrayList<>();
        }

        orderDetails.add(tempOrderDetails);

        tempOrderDetails.setOrder(this);
    }

}
