package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentHashMap;

import java.util.List;

@Controller
//@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private CartService cartService;

    // Map to store locks for each book
    private final ConcurrentHashMap<Long, Lock> bookLocks = new ConcurrentHashMap<>();


    /**
     * Handles the request to display the form for adding a new book.
     * This method is mapped to the "/addBook" URL and is triggered by a GET request.
     * It prepares a new, empty Book object and adds it to the model, so the form
     * can be populated with the book's data. The method then returns the view name
     * "books/addBook" to show the form.
     *
     * @param model The model to which the new Book object will be added.
     * @return The name of the view to display the add book form.
     */
    @GetMapping("/addBook")
    public String getAddBookForm(Model model) {
        model.addAttribute("book",new Book());

        return "books/addBook";
    }

    /**
     * Handles the submission of the form for adding a new book.
     * This method is mapped to the "/addBook" URL and is triggered by a POST request.
     * It receives the book data submitted by the form through the `@ModelAttribute`.
     * The method sets the book's status to "active" and then calls the `bookService`
     * to save the book in the system. After successfully adding the book, it redirects
     * the user to the "/bookList" page to view the list of all books.
     *
     * @param theBook The Book object containing the data submitted by the form.
     * @param model The model to which a success message will be added.
     * @return The name of the view to redirect to after adding the book.
     */
    @PostMapping("/addBook")
    public String addBookForm(@ModelAttribute Book theBook, Model model) {
        theBook.setIsActive("active");
        bookService.addBook(theBook);

        model.addAttribute("message", "The Book has been added successfully");

        return "books/addBook";
    }

    /**
     * Handles the request to display the list of all books.
     * This method is mapped to the "/bookList" URL and is triggered by a GET request.
     * It retrieves all books from the `bookService`, adds them to the model, and
     * returns the view name "books/bookList" to display the books.
     *
     * @param model The model to which the list of books will be added.
     * @return The name of the view to display the list of books.
     */
    @GetMapping("/bookList")
    public String getListBooks(Model model) {

        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        return "books/bookList"; // Return the view to display the books
    }


    /**
     * Handles the request to search for books based on a query.
     * This method is mapped to the "/searchInBookList" URL and is triggered by a GET request.
     * It searches for books that match the given query, adds the results to the model,
     * and returns the view "books/bookList" to display the search results.
     *
     * @param query The search query to find books.
     * @param model The model to which the list of matching books will be added.
     * @return The name of the view to display the search results.
     */
    @GetMapping("/searchInBookList")
    public String searchForBookInBookList(@RequestParam("query") String query,
                                          Model model) {

        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);

        return "/books/bookList";
    }

    /**
     * Handles the request to display the items available for sale.
     * This method is mapped to the "/itemSells" URL and is triggered by a GET request.
     * It retrieves the current user from the session, adds the user to the model if logged in,
     * retrieves all active books and subjects, and returns the view "books/itemSells" to display the items.
     *
     * @param model The model to which the list of books and subjects will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the items available for sale.
     */
    @GetMapping("/itemSells" )
    public String getItemsForSell(Model model , HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        if (!(currentUser == null)) {
            model.addAttribute("currentUser",currentUser);
        }

        List<Book> allBooks = bookService.getBooksExceptInactive();

        model.addAttribute("allBooks", allBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());

        return "books/itemSells";
    }

    /**
     * Handles the request to add an item (book) to the user's cart.
     * This method is mapped to the "/itemSells/{id}" URL and is triggered by a POST request.
     * It checks if the book is available and if the book is already in the user's cart.
     * If the book is available and not already in the cart, it adds the book to the cart.
     * It sets appropriate feedback messages and redirects to the "itemSells" page.
     *
     * @param session The HTTP session from which the current user will be retrieved.
     * @param id The ID of the book to be added to the cart.
     * @param redirectAttributes The redirect attributes to which feedback messages will be added.
     * @return A redirect URL to the itemSells page.
     */
    @Transactional
    @PostMapping("/itemSells/{id}")
    public String addItemToCart(HttpSession session ,@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {

        boolean isExistTheBookId = false;

        // Check if the book is available in the inventory
        boolean isAvailable = bookService.isBookAvailable(id);

        if (isAvailable) {
            User currentUser = (User) session.getAttribute("user");

            if (!(currentUser == null)) {

                List<CartItems> cartItemsList = cartService.getCartForUser(currentUser);

                for(CartItems tempItem : cartItemsList) {
                    if (tempItem.getBook().getBookId() == id) {
                        isExistTheBookId = true;
                        break;
                    }
                }

                if (isExistTheBookId) {
                    // Set a feedback message
                    redirectAttributes.addFlashAttribute("warningMessage", "Book is already in the cart!");
                }
                else{
                    cartService.addToCart(currentUser,id);
                    // Set a feedback message
                    redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");
                }

            }
        }
        else {
            // Set an error message for out-of-stock
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, this book is out of stock!");
        }


        return "redirect:/itemSells";
    }

    /**
     * Handles the request to filter books based on a selected subject.
     * This method is mapped to the "/filterBooks" URL and is triggered by a GET request.
     * It filters the books by the given subject, adds the filtered books and subjects to the model,
     * and returns the view "books/itemSells" to display the filtered books.
     *
     * @param subject The subject to filter books by.
     * @param model The model to which the filtered books and subjects will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the filtered books.
     */
    @GetMapping("/filterBooks")
    public String filterForBooks(@RequestParam("subject") String subject,
                                 Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        if (!(currentUser == null)) {
            model.addAttribute("currentUser",currentUser);
        }

        List<Book> filteredBooks;
        if (subject != null && !(subject.equals("all")) ) {
            filteredBooks = bookService.getBooksBySubject(subject);

        } else {
            filteredBooks = bookService.getBooksExceptInactive();
        }

        model.addAttribute("allBooks", filteredBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());

        return "books/itemSells"; // Return the view name where books are displayed
    }

    /**
     * Handles the request to search for books based on a query.
     * This method is mapped to the "/search" URL and is triggered by a GET request.
     * It searches for books that match the given query, adds the results to the model,
     * and returns the view "books/itemSells" to display the search results.
     *
     * @param query The search query to find books.
     * @param model The model to which the list of matching books will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the search results.
     */
    @GetMapping("/search")
    public String searchForBooks(@RequestParam("query") String query,
                                 Model model,HttpSession session) {

        User currentUser = (User) session.getAttribute("user");

        if (!(currentUser == null)) {
            model.addAttribute("currentUser",currentUser);
        }

        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("allBooks", books);
        return "books/itemSells";
    }

    /**
     * Handles the request to add an item (book) to the cart from the book details page.
     * This method is mapped to the "bookDetails/itemSells/{id}" URL and is triggered by a POST request.
     * It checks if the book is available and if the book is already in the user's cart.
     * If the book is available and not already in the cart, it adds the book to the cart.
     * It sets appropriate feedback messages and redirects the user back to the book details page.
     *
     * @param model The model to which the current user will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @param id The ID of the book to be added to the cart.
     * @param authentication The authentication object to obtain the current user details.
     * @param redirectAttributes The redirect attributes to which feedback messages will be added.
     * @return A redirect URL to the book details page.
     */
    @PostMapping("bookDetails/itemSells/{id}")
    public String addItemToCartFromDetailBook(Model model,
                                              HttpSession session ,
                                              @PathVariable Long id,
                                              @AuthenticationPrincipal Authentication authentication,
                                              RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);

            boolean isExistTheBookId = false;

            // Check if the book is available in the inventory
            boolean isAvailable = bookService.isBookAvailable(id);


            if (isAvailable) {

                List<CartItems> cartItemsList = cartService.getCartForUser(currentUser);

                for(CartItems tempItem : cartItemsList) {
                    if (tempItem.getBook().getBookId() == id) {
                        isExistTheBookId = true;
                        break;
                    }
                }
                if (isExistTheBookId) {
                    redirectAttributes.addFlashAttribute("warningMessage", "Book is already in the cart!");
                }
                else{
                    cartService.addToCart(currentUser,id);
                    redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");
                }
            }
            else{
                redirectAttributes.addFlashAttribute("errorMessage", "Sorry, this book is out of stock!");
            }
        }
        return "redirect:/bookDetails/{id}";

    }

    /**
     * Handles the request to display the details of a specific book.
     * This method is mapped to the "/bookDetails/{id}" URL and is triggered by a GET request.
     * It retrieves the book by its ID, adds the book and current user (if logged in) to the model,
     * and returns the view "books/bookDetails" to display the book's details.
     *
     * @param id The ID of the book to display details for.
     * @param model The model to which the book and current user (if logged in) will be added.
     * @param session The HTTP session from which the current user will be retrieved.
     * @return The name of the view to display the book's details.
     */
    @GetMapping("/bookDetails/{id}")
    public String getBookDetailsPage(@PathVariable Long id, Model model,
                                     HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser", currentUser);
        }

        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "books/bookDetails";
    }

    /**
     * Handles the request to display the edit form for a specific book.
     * This method is mapped to the "/editBook/{id}" URL and is triggered by a GET request.
     * It retrieves the book by its ID, adds the book to the model, and returns the view "books/editBook"
     * to display the form for editing the book's details.
     *
     * @param id The ID of the book to be edited.
     * @param model The model to which the book will be added.
     * @return The name of the view to display the edit book form.
     */
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "books/editBook";
    }

    /**
     * Handles the submission of the form to update a book's details.
     * This method is mapped to the "/editBook/{id}" URL and is triggered by a POST request.
     * It updates the book's information in the database, including handling an image file upload if provided.
     * After updating the book, it sets a success message and redirects to the book list page.
     *
     * @param model The model to which the success message will be added.
     * @param id The ID of the book to be updated.
     * @param theBook The Book object containing the updated details.
     * @param imageFile The image file to be uploaded for the book.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the book list page.
     */
    /*
    @PostMapping("/editBook/{id}")
    public String updateBook(Model model ,
                             @PathVariable Long id,
                             @ModelAttribute Book theBook,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {

        // Handle file upload and update the book's image field as needed
        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            // Save the file and update book's image field
            theBook.setImage(fileName);
        }

        bookService.update(id, theBook);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully!");

        return "redirect:/bookList";
    }

     */

    @PostMapping("/editBook/{id}")
    public String updateBook(Model model ,
                             @PathVariable Long id,
                             @ModelAttribute Book theBook,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {


        // Acquire a lock for the specific book by its ID
        Lock lock = bookLocks.computeIfAbsent(id, key -> new ReentrantLock());


        // Attempt to lock the book before updating its details
        lock.lock();
        try {
            // Handle file upload and update the book's image field if necessary
            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                // Save the file and update book's image field
                theBook.setImage(fileName);
            }

            // Update the book's details in the database
            bookService.update(id, theBook);

            // Add success message after the update
            redirectAttributes.addFlashAttribute("message", "Book updated successfully!");

        }
        finally {
            // Always release the lock to avoid deadlocks
            lock.unlock();
            // Optionally clean up the lock after the update
            bookLocks.remove(id);
        }
        return "redirect:/bookList";
    }


    /**
     * Handles the request to change a book's status to "active".
     * This method is mapped to the "/toActive/{id}" URL and is triggered by a POST request.
     * It retrieves the book by its ID, changes its status to "active", saves the updated book,
     * and sets a success message. Finally, it redirects the user to the book list page.
     *
     * @param model The model to which the list of books will be added.
     * @param id The ID of the book to be changed to active.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the book list page.
     */
    @PostMapping("/toActive/{id}")
    public String changeBookToActive(Model model,@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(id);
        book.setIsActive("active");

        bookService.addBook(book);

        redirectAttributes.addFlashAttribute("message", "Book updated to active successfully!");

        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        return "redirect:/bookList";


    }

    /**
     * Handles the request to change a book's status to "inactive".
     * This method is mapped to the "/toInactive/{id}" URL and is triggered by a POST request.
     * It retrieves the book by its ID, changes its status to "inactive", saves the updated book,
     * and sets a success message. Finally, it redirects the user to the book list page.
     *
     * @param model The model to which the list of books will be added.
     * @param id The ID of the book to be changed to inactive.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the book list page.
     */
    @PostMapping("/toInactive/{id}")
    public String changeBookToInactive(Model model,@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(id);
        book.setIsActive("inactive");

        bookService.addBook(book);

        redirectAttributes.addFlashAttribute("message", "Book updated to inactive successfully!");

        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);


        model.addAttribute("message", "Password changed successfully.");

        return "redirect:/bookList";
    }

    /**
     * Handles the request to delete a book.
     * This method is mapped to the "/delete/{id}" URL and is triggered by a POST request.
     * It deletes the book by its ID, sets a success message, and redirects the user to the book list page.
     *
     * @param id The ID of the book to be deleted.
     * @param redirectAttributes The redirect attributes to which a success message will be added.
     * @return A redirect URL to the book list page.
     */
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Book was deleted successfully!");
        return "redirect:/bookList";
    }

}