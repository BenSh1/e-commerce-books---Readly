package com.ecommerce.ecommerce.user;


import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private int id;
    private String name;
    private String email;
    private String password;
    private String userName;
    private String address;
    private String created_at;

    public User(int id, String name, String email, String password, String userName, String address, String created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.address = address;
        this.created_at = created_at;
    }

    public User(String name, String address, String userName, String password, String email, int id) {
        this.name = name;
        this.address = address;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public User() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getAddress() {
        return address;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
