package su.library.Books;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String bookId){
        return bookRepository.findBookByBookId(bookId);
    }

    public Book setAddBook(Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> getDeleteById(String bookId){
        return bookRepository.deleteBookByBookId(bookId);
    }

    public Optional<Book> setUpdateBookById(String bookId, Book book){
        return bookRepository.findById(bookId).map(exitingBook -> {
            exitingBook.setBookName(book.getBookName());
            exitingBook.setBookAuthor(book.getBookAuthor());
            exitingBook.setBookPoster(book.getBookPoster());
            exitingBook.setBookSource(book.getBookSource());
            exitingBook.setBookUseLanguage(book.getBookUseLanguage());
            exitingBook.setBookType(book.getBookType());
            exitingBook.setBookDescribe(book.getBookDescribe());
            exitingBook.setBookTypeCode(book.getBookTypeCode());
            return bookRepository.save(exitingBook);
        });
    }

}
