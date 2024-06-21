package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.ServiceController;
import com.ecommerce.ecommerce.dao.BookDao;
//import com.ecommerce.ecommerce.dao.BookRepository;
import com.ecommerce.ecommerce.dao.BookRepository;
import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";


    @Autowired
    private BookDao bookDao;

/*
    @GetMapping("ben")
    public String showBookList(Model model) {
        List<Book> books = bookDao.findAll();

        model.addAttribute("books", books);
        return "bookList"; // Return the view to display the books
    }

 */
    @GetMapping("/addBook2")
    public String addBookForm2(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook2";
    }

    @PostMapping("/addBook2")
    public String addBook2(@ModelAttribute Book theBook) {
        bookDao.save(theBook);
        //bookRepository.save(theBook);
        return "redirect:/bookList2";
    }
    @GetMapping("/bookList2")
    public String listBooks2(Model model) {
        //List<Book> books = bookService.getBooks();
        //List<Book> books = bookRepository.findAll();
        List<Book> books = bookService.getBooks();

        model.addAttribute("books", books);
        return "bookList2"; // Return the view to display the books
    }

    @GetMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook";
    }
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book theBook) {
        bookDao.save(theBook);
        //bookRepository.save(theBook);
        return "redirect:/bookList";
    }
    @GetMapping("/bookList")
    public String listBooks(Model model) {
        //List<Book> books = bookService.getBooks();
        //List<Book> books = bookRepository.findAll();
        List<Book> books = bookService.getBooks();

        model.addAttribute("books", books);
        return "bookList"; // Return the view to display the books
    }


/*
    @GetMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook";
    }

    @PostMapping("/addBook2")
    public String addBook(@ModelAttribute Book theBook) {
        bookDao.save(theBook);
        return "redirect:/bookList";
    }

    @PostMapping("/bookList")
    public String processAddBookForm(@ModelAttribute("book") Book theBook,
                                     Model model)
    {

        //try {
        //     theBook.setImage(image.getBytes());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}


        System.out.println("processAddBookForm");
        System.out.println("title : " + theBook.getTitle());
        System.out.println("category : " + theBook.getCategory());

        if (theBook.getImageBase64() != null && !theBook.getImageBase64().isEmpty()) {
            String base64Image = Base64.getEncoder().encodeToString(theBook.getImageBase64().getBytes());
            System.out.println("base64Image : " + base64Image);
            theBook.setImageBase64(base64Image);
        }

        bookService.addBook(theBook);

        model.addAttribute("books", bookService.getBooks());


        return "bookList"; // Show the updated book list
    }
*/


    @GetMapping("/itemSells2")
    public String getItems(Model model) {
        //List<Book> books = bookRepository.findAll();
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "itemSells2";
    }

    @GetMapping("/itemSells3")
    public String getItems3(Model model) {
        //List<Book> books = bookRepository.findAll();
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "itemSells3";
    }

    /*
    @GetMapping("/bookList")
    public String showBookList() {
        return "bookList";
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

    @GetMapping("/bookList")
    public String showBookList() {
        return "bookList";
    }

     */


    /*
        @GetMapping("/addBook")
    public String addBookForm() {
        return "addBook";
    }

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
