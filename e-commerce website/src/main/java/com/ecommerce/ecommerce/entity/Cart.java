package com.ecommerce.ecommerce.entity;


import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cartId")
    private int cartId;
    @Column(name="cusotmerId")
    private int customerId;






}

