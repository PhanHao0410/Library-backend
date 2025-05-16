package su.library.BookType;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;
import su.library.DTOClasses.BookDTO;

@Data
@NoArgsConstructor

public class Book {

    private String bookId;

    private String bookName;

    private String bookAuthor;

    private String bookUseLanguage;

    private String bookPoster;

    private String bookSource;

    private String bookType;

    private String bookDescribe;

    private String bookStatus;

    private String bookStatusCode;

    private String expectedTime;

    private String spentTime;

    private ObjectId bookFileId;

    public Book(String bookId, BookDTO newBook, ObjectId bookFileId) {
        super();
        this.bookId = bookId;
        this.bookAuthor = newBook.getBookAuthor();
        this.bookName = newBook.getBookName();
        this.bookUseLanguage = newBook.getBookUseLanguage();
        this.bookPoster = newBook.getBookPoster();
        this.bookSource = newBook.getBookSource();
        this.bookDescribe = newBook.getBookDescribe();
        this.bookType = newBook.getBookType();
        this.bookStatus = newBook.getBookStatus();
        this.bookStatusCode = newBook.getBookStatusCode();
        this.expectedTime = newBook.getExpectedTime();
        this.spentTime = newBook.getSpentTime();
        this.bookFileId = bookFileId;
    }

    public String getBookFileId() {
        return bookFileId != null ? bookFileId.toHexString() : null;
    }

}
