package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId")
    private int bookId;
    @Column(name="title")
    private String title;
    @Column(name="authorId")
    private int authorId;
    @Column(name="categoryID")
    private int categoryID;
    @Column(name="price")
    private int price;
    @Column(name="stock")
    private int stock;


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", categoryID=" + categoryID +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
