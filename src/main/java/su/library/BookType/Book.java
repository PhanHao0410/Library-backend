package su.library.BookType;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Book(String bookId, Book newBook) {
        super();
        this.bookId = bookId;
        this.bookAuthor = newBook.bookAuthor;
        this.bookName = newBook.bookName;
        this.bookUseLanguage = newBook.bookUseLanguage;
        this.bookPoster = newBook.bookPoster;
        this.bookSource = newBook.bookSource;
        this.bookDescribe = newBook.bookDescribe;
        this.bookType = newBook.bookType;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookUseLanguage() {
        return bookUseLanguage;
    }

    public void setBookUseLanguage(String bookUseLanguage) {
        this.bookUseLanguage = bookUseLanguage;
    }

    public String getBookPoster() {
        return bookPoster;
    }

    public void setBookPoster(String bookPoster) {
        this.bookPoster = bookPoster;
    }

    public String getBookSource() {
        return bookSource;
    }

    public void setBookSource(String bookSource) {
        this.bookSource = bookSource;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getBookDescribe() {
        return bookDescribe;
    }

    public void setBookDescribe(String bookDescribe) {
        this.bookDescribe = bookDescribe;
    }

}
