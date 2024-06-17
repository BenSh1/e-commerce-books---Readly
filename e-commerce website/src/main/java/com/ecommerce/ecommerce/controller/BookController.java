package com.ecommerce.ecommerce.controller;


import com.ecommerce.ecommerce.entity.Book;
import com.ecommerce.ecommerce.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;



    private static String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("/addBook")
    public String addBookForm() {
        return "addBook";
    }
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
