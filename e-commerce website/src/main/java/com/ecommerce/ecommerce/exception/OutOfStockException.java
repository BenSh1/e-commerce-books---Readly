package com.ecommerce.ecommerce.exception;

public class OutOfStockException extends RuntimeException {
    private final String bookTitle;
    public OutOfStockException(String bookTitle) {
        super("Book out of stock: " + bookTitle);
        this.bookTitle = bookTitle;
    }
    public String getBookTitle() {
        return bookTitle;
    }
}
