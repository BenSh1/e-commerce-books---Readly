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
        /*
        model.addAttribute("message", "Book added to cart successfully!");
        System.out.println("=================================================================");
         */

        return "itemSells";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        return "itemSells";
    }


/*
    @GetMapping("/itemSells/{id}")
    public String getItemSells(@PathVariable Long id, Model model) {
        // Get the list of books on sale
        //List<Book> books = bookService.getAllBooksOnSale();
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);

        // Add the item ID to the model (if needed for further logic)
        model.addAttribute("itemId", id);

        return "itemSells";
    }
    */

    @PostMapping("/itemSells/{id}")
    public String addItemToCart(Model model, HttpSession session ,@PathVariable Long id,
                                @AuthenticationPrincipal Authentication authentication,
                                            RedirectAttributes redirectAttributes) {

        // Check if the book is available in the inventory
        boolean isAvailable = bookService.isBookAvailable(id);

        if (isAvailable) {
            System.out.println("------------in addItemToCart--------------------------------------------------");
            //System.out.println("id: " + id );
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                throw new RuntimeException("User not logged in");
            }
            //System.out.println("user : " + currentUser.toString());

            cartService.addToCart(currentUser,id);

            // Set a feedback message
            redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");

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
                                RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("user : " + currentUser.toString());

        cartService.addToCart(currentUser,id);
        redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");

        return "redirect:/bookDetails/{id}"; // Redirect back to the items sell page
    }
    /*
    @PostMapping("/bookDetails/{id}")
    public String addBook(@PathVariable Long id, Model model, HttpSession session ,) {

        User currentUser = (User) session.getAttribute("user");
        //User user = userService.get
        if (currentUser == null) {
            throw new RuntimeException("User not logged in");
        }
        System.out.println("currentUser.getFirstName()  = " + currentUser.getFirstName());

        List<CartItems> cartItems = cartService.getCartForUser(currentUser);

        cartService.addToCart(currentUser,id,  quantity);

        return "bookDetails";
    }

     */


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

    //@PostMapping("/{cartId}/books/{bookId}")
    @PostMapping("/cart/add")
    public String addBookToCart(@PathVariable Long cartId, @PathVariable Long bookId, @RequestParam int quantity) {
        /*
        try {
            cartService.addToCart(cartId, bookId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book to cart");
        }

         */

        //cartService.addToCart(cartId, bookId ,quantity);

        /*
        Book book = bookService.findById(bookId);
        if (book != null) {
            cartService.addToCart2(book, quantity);
        }

         */

        // Redirect to cart view or display confirmation message (optional)
        return "redirect:/cart";
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

        //return "redirect:/editBook";
        return "editBook";
    }
/*
    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        System.out.println("==============================================================");
        System.out.println("Search query: " + query);
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        return "search";
    }

 */



/*
    @GetMapping("/bookImage/{id}")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {

        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b WHERE b.id = :id", Book.class);
        query.setParameter("id", id);
        Book book = query.getSingleResult();

        if (book != null && book.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "image/jpeg");
            return new ResponseEntity<>(book.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

 */

/*
    @GetMapping("/bookImage/{id}")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        System.out.println("bookService.getBook(id) : " + bookService.getBook(id));

        System.out.println(book.getImageBase64());
        if (book != null && book.getImage() != null) {
            System.out.println("checking inside ");

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, "image/png");
            return new ResponseEntity<>(book.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

 */

    /*
    @PostMapping("/addBook")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("description") String description,
                          @RequestParam("category") String category,
                          @RequestParam("price") String price,
                          @RequestParam("stock") String stock,
                          @RequestParam("image") MultipartFile image,
                          Model model) {
        System.out.println("title : " + title);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthorName(author);
        book.setDescription(description);
        try {
            book.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookService.addBook(book);

        model.addAttribute("books", bookService.getBooks());
        return "bookList"; // Show the updated book list
    }

     */


    /*
    @PostMapping("/addBook")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("description") String description,
                          @RequestParam("image") MultipartFile image,
                          Model model) {

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        try {
            book.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookService.addBook(book);

        model.addAttribute("books", bookService.getBooks());
        return "bookList"; // Show the updated book list
    }

     */



/*
    @PostMapping("/addBook")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("description") String description,
                          @RequestParam("image") MultipartFile image,
                          Model model) {

        Book book = new Book();
        book.setTitle(title);
        book.setAuthorName(author);
        book.setDescription(description);
        try {
            book.setImage(image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookService.addBook(book);

        model.addAttribute("books", bookService.getBooks());
        return "bookList"; // Show the updated book list
    }

 */

/*
    @GetMapping("/book/image/{id}")
    @ResponseBody
    public byte[] getBookImage(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        return book != null ? book.getImage() : null;
    }

 */


/*
    @PostMapping("/addBook")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("description") String description,
                          @RequestParam("image") MultipartFile image,
                          Model model) {

        // Save the image
        //String imageUrl = saveImage(image);

        // Create a new book and add it to the service
        Book book = new Book();
        book.setTitle(title);
        book.setAuthorName(author);
        //book.setDescription(description);
        //book.setImageUrl(imageUrl);
        bookService.addBook(book);

        model.addAttribute("books", bookService.getBooks());
        return "bookList"; // Show the updated book list
    }
*/

/*
    private String saveImage(MultipartFile image) {
        if (image.isEmpty()) {
            return null;
        }

        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + image.getOriginalFilename());
            Files.write(path, bytes);
            return "/images/" + image.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

 */

}
