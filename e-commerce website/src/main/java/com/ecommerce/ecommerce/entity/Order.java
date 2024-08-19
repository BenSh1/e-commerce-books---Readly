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

    @Column(name="status")
    private String status;


    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private List<OrderDetails> orderDetails;



    /**
     * Getters + Setters
     *
     */
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    /**
     * This method adds the specified {@link OrderDetails} to the list of order details for this order.
     * If the list of order details is {@code null}, it initializes a new {@link ArrayList}.
     * It also sets the {@link Order} reference in the {@link OrderDetails} object to this order.
     *
     * @param tempOrderDetails the {@link OrderDetails} to be added to the order.
     */
    public void add(OrderDetails tempOrderDetails) {

        if(orderDetails == null) {
            orderDetails = new ArrayList<>();
        }

        orderDetails.add(tempOrderDetails);

        tempOrderDetails.setOrder(this);
    }


    /**
     * Returns a string representation of the Order object.
     *
     * @return a string representation of the Order object.
     */

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }


}
