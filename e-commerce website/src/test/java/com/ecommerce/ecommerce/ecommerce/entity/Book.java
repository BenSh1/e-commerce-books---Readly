package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name="book")
public class Book {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="description" , length = 10000)
    private String description;
    @Column(name="category")
    private String category;
    @Column(name="price")
    private int price;
    @Column(name="stock")
    private int stock;

/*
    @Column(name="image")
    @Lob
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

 */


    @Column(name="image")
    //@Lob
    private String image;


    @Column(name="isActive")
    private String isActive;

    public Book() {

    }

    public Book(String title, String author, String description,
                String category, int price, int stock, String image) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    /*
    @Column(name="image")
    private String image;

 */


/*
    @Transient
    private String imageBase64;


    // Add this method to convert image to Base64
    public String getImageBase64() {
        if (this.image != null) {
            String base64 = java.util.Base64.getEncoder().encodeToString(this.image);
            System.out.println("Base64 Image: " + base64);
            return base64;
        }
        return null;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

 */

/*
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

 */




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorName() {
        return author;
    }



    public int getBookId() {
        return id;
    }

    public void setBookId(int bookId) {
        this.id = bookId;
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

    public void setCategory(String categoryName) {
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


    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + author +
                ", categoryID=" + category +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
