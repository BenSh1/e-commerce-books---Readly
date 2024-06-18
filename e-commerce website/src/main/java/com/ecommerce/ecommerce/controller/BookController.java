package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.service.BookService;
import com.ecommerce.ecommerce.user.WebUser;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book",new Book());
        System.out.println("addBookForm");
        return "addBook";
    }



    @PostMapping("/bookList")
    public String processAddBookForm(@ModelAttribute("book") Book theBook, Model model)
    {
        System.out.println("processAddBookForm");

        System.out.println("title : " + theBook.getTitle());
        System.out.println("title : " + theBook.getCategory());

        bookService.addBook(theBook);

        model.addAttribute("books", bookService.getBooks());
        return "bookList"; // Show the updated book list
    }
    /*
    @GetMapping("/bookList")
    public String showBookList() {
        return "bookList";
    }

     */


    @GetMapping("/bookList")
    public String listBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "bookList"; // Return the view to display the books
    }



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
