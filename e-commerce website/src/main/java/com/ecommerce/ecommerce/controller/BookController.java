package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.dao.BookDao;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.entity.CartItems;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.service.CartService;
import com.ecommerce.ecommerce.service.OrderService;
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
//@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/addBook")
    public String getAddBookForm(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook";
    }

    @PostMapping("/addBook")
    public String addBookForm(@ModelAttribute Book theBook) {
        theBook.setIsActive("active");
        bookService.addBook(theBook);
        return "redirect:/bookList";
    }

    @GetMapping("/bookList")
    public String getListBooks(Model model) {

        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        return "bookList"; // Return the view to display the books
    }

    @GetMapping("/itemSells" )
    //@GetMapping("/itemsForSell" )
    public String getItemsForSell(Model model , HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);
        }

        //List<Book> allBooks = bookService.getBooks();
        List<Book> allBooks = bookService.getBooksExceptInactive();

        model.addAttribute("allBooks", allBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());

        return "itemSells";
        //return "itemsForSell";

    }

    @Transactional
    @PostMapping("/itemSells/{id}")
    //@PostMapping("/itemsForSell/{id}")
    public String addItemToCart(Model model, HttpSession session ,@PathVariable Long id,
                                @AuthenticationPrincipal Authentication authentication,
                                RedirectAttributes redirectAttributes) {

        boolean isExistTheBookId = false;

        // Check if the book is available in the inventory
        boolean isAvailable = bookService.isBookAvailable(id);

        if (isAvailable) {
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

    @GetMapping("/filterBooks")
    public String filterForBooks(@RequestParam("subject") String subject,
                              Model model, HttpSession session) {
        //System.out.println("==================inside filterBooks==========");

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);
        }

        List<Book> filteredBooks;
        if (subject != null && !(subject.equals("all")) ) {
            filteredBooks = bookService.getBooksBySubject(subject);

        } else {
            //filteredBooks = bookService.getBooks();
            filteredBooks = bookService.getBooksExceptInactive();
        }

        model.addAttribute("allBooks", filteredBooks);
        model.addAttribute("subjects", bookService.getAllSubjects());
/*
        if(filteredBooks.isEmpty())
        {
            model.addAttribute("MessageNoBooksExist","No Books exist to this subject!");
        }

 */

        return "itemSells"; // Return the view name where books are displayed
    }

    @GetMapping("/search")
    public String searchForBooks(@RequestParam("query") String query,
                              Model model,HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            //throw new RuntimeException("User not logged in");
            System.out.println("===User not logged in=======================");
        }
        else{
            System.out.println("==========currentUser======" + currentUser.getUserName());
            model.addAttribute("currentUser",currentUser);
        }

        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("allBooks", books);
        return "itemSells";
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
                    //cartService.addToCartWithQuantity(currentUser,id,quantity);
                    redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");
                }
            }
            else{
                redirectAttributes.addFlashAttribute("errorMessage", "Sorry, this book is out of stock!");
            }
        }
        return "redirect:/bookDetails/{id}"; // Redirect back to the items sell page
    }

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

    @PostMapping("/toActive/{id}")
    public String changeBookToActive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(id);
        book.setIsActive("active");
        bookDao.save(book);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully!");

        return "redirect:/bookList";
    }

    @PostMapping("/toInactive/{id}")
    public String changeBookToInactive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Book book = bookService.getBook(id);
        book.setIsActive("inactive");
        bookDao.save(book);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully!");

        return "redirect:/bookList";
    }



    // checking the images
    /*
    @GetMapping("/books/{id}/image")
    public String showImage (@PathVariable Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "checking";
    }

     */

    /*
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

 */



/*
    private User getCurrentUser(HttpSession session){
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        return currentUser;
    }

    private User getCurrentUser(HttpSession session) {
        try {
            return (User) session.getAttribute("currentUser");
        } catch (Exception e) {
            // Return null if no user is logged in
            return null;
        }
    }
 */













}
