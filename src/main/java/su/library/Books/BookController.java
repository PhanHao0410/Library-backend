package su.library.Books;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @GetMapping
    public ResponseEntity<List<Book>> allBooks() {
        return new ResponseEntity<List<Book>>(bookService.getAllBook(), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Optional<Book>> getSingleBook(@PathVariable String bookId) {
        return new ResponseEntity<Optional<Book>>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(bookService.setAddBook(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Optional<Book>> deleteBook(@PathVariable String bookId) {
        return new ResponseEntity<Optional<Book>>(bookService.getDeleteById(bookId), HttpStatus.OK);
    }
    
    @PutMapping("/update/{bookId}")
    public ResponseEntity<Optional<Book>> updateBookById(@PathVariable String bookId, @RequestBody Book book) {
        return new ResponseEntity<Optional<Book>>(bookService.setUpdateBookById(bookId,book), HttpStatus.OK);
    }
    
    
    
}
