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

    @Column(name="image")
    private String image;

    @Column(name="isActive")
    private String isActive;

    /**
     * Default constructor for the Book class.
     * This constructor initializes a new Book object without setting any attributes.
     */
    public Book() {

    }


    /**
     * Parameterized constructor for the Book class.
     * This constructor initializes a new Book object with the specified attributes.
     *
     * @param title       the title of the book.
     * @param author      the author of the book.
     * @param description a brief description of the book.
     * @param category    the category of the book.
     * @param price       the price of the book.
     * @param stock       the number of copies in stock.
     * @param isActive    a flag indicating if the book is active.
     * @param image       the book's image.
     */
    public Book(String title, String author, String description,
                String category, int price, int stock,String isActive, String image) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
        this.image = image;
    }

    /**
     * Getters + Setters
     *
     */
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

    /**
     * Returns a string representation of the Book object.
     *
     * @return a string representation of the Book object.
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
