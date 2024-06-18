package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="book")
public class Book {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId")
    private int bookId;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="description")
    private String description;
    @Column(name="category")
    private String category;
    @Column(name="price")
    private int price;
    @Column(name="stock")
    private int stock;

    @Lob
    @Column(name="image")
    private byte[] image;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return author;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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

    public void setAuthorName(String authorName) {
        this.author = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategoryID(String categoryName) {
        this.category = categoryName;
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
                ", authorId=" + author +
                ", categoryID=" + category +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
