package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.dao.BookDao;
//import com.ecommerce.ecommerce.dao.BookRepository;
//import com.ecommerce.ecommerce.dao.BookRepository;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
/*
    @Autowired
    private BookRepository bookRepository;

 */

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CartService cartService;

    @GetMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook";
    }
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book theBook) {
        /*
        try{
            bookService.addBook(theBook);
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
        bookService.addBook(theBook);
        //bookRepository.save(theBook);
        return "redirect:/bookList";
    }

    // checking the images
    @GetMapping("/books/{id}/image")
    public String showImage (@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "checking";
    }

    @GetMapping("/bookList")
    public String listBooks(Model model) {
        //List<Book> books = bookService.getBooks();
        //List<Book> books = bookRepository.findAll();
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        return "bookList"; // Return the view to display the books
    }

    @GetMapping("/itemSells")
    public String getItems(Model model) {
        //List<Book> books = bookRepository.findAll();

        List<Book> allBooks = bookService.getBooks();
        model.addAttribute("allBooks", allBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());

        return "itemSells";
    }

    @GetMapping("/filterBooks")
    public String filterBooks(@RequestParam("subject") String subject, Model model) {
        List<Book> filteredBooks;
        if (subject != null && !(subject.equals("all")) ) {
            filteredBooks = bookService.getBooksBySubject(subject);
        } else {
            filteredBooks = bookService.getBooks();
        }
        model.addAttribute("allBooks", filteredBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());

        return "itemSells"; // Return the view name where books are displayed
    }


    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        //model.addAttribute("books", books);
        model.addAttribute("allBooks", books);
        return "itemSells";
    }

    @Transactional
    @PostMapping("/itemSells/{id}")
    public String addItemToCart(Model model, HttpSession session ,@PathVariable Long id,
                                @AuthenticationPrincipal Authentication authentication,
                                            RedirectAttributes redirectAttributes) {

        boolean isExistTheBookId = false;

        // Check if the book is available in the inventory
        boolean isAvailable = bookService.isBookAvailable(id);

        if (isAvailable) {
            System.out.println("------------in addItemToCart--------------------------------------------------");
            //System.out.println("id: " + id );
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                throw new RuntimeException("User not logged in");
            }


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
        else {
            // Set an error message for out-of-stock
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, this book is out of stock!");
        }

        return "redirect:/itemSells";
    }




    @PostMapping("bookDetails/itemSells/{id}")
    public String addItemToCartFromDetailBook(Model model,
                                              HttpSession session ,
                                              @PathVariable Long id,
                                @AuthenticationPrincipal Authentication authentication,
                                              @RequestParam("quantity") int quantity,
                                RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }

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
                //cartService.addToCartWithQuantity(currentUser,id,quantity);
                redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, this book is out of stock!");
        }


        return "redirect:/bookDetails/{id}"; // Redirect back to the items sell page
    }

    @GetMapping("/bookDetails/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "bookDetails";
    }

    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "editBook";
    }
    @PostMapping("/editBook/{id}")
    public String updateBook(Model model ,
                             @PathVariable Long id,
                             @ModelAttribute Book theBook,
                             RedirectAttributes redirectAttributes) {

        /*Book book = bookService.getBook(id);
        model.addAttribute("book", book);*/
        bookService.update(id, theBook);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
        return "redirect:/bookList";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
        return "redirect:/bookList";
    }

    @GetMapping("/editBook")
    public String showEditBookPage(Model model, @RequestParam long id) {

        try{
            Book book = bookDao.findById(id);
            model.addAttribute("book", book);

            Book editedBook = new Book();
            editedBook.setTitle(book.getTitle());
            editedBook.setAuthorName(book.getAuthorName());
            editedBook.setCategory(book.getCategory());
            editedBook.setDescription(book.getDescription());
            editedBook.setPrice(book.getPrice());
            editedBook.setStock(book.getStock());
            editedBook.setImage(book.getImage());

            model.addAttribute("editedBook", editedBook);

        }catch (Exception e)
        {
            System.out.println("Exception in showEditBookPage: " + e.getMessage());
            return "redirect:/bookList";

        }

        return "editBook";
    }
















}
