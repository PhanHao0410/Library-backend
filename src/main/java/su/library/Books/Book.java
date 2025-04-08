package su.library.Books;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Book {

    @Id

    private String bookId;

    private String bookName;

    private String bookAuthor;

    private String bookUseLanguage;

    private String bookPoster;

    private String bookSource;

    private String bookType;

    private String bookDescribe;

    private String bookTypeCode;

    public Book(String bookName, String bookAuthor, String bookUseLanguage, String bookPoster, String bookSource, String bookType, String bookDescribe, String bookTypeCode){
        super();
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookUseLanguage = bookUseLanguage;
        this.bookPoster = bookPoster;
        this.bookSource = bookSource;
        this.bookType = bookType;
        this.bookDescribe = bookDescribe;
        this.bookTypeCode = bookTypeCode;
    }

    public String getBookName(){
        return bookName;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public String getBookAuthor(){
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor){
        this.bookAuthor = bookAuthor;
    }

    public String getBookUseLanguage(){
        return bookUseLanguage;
    }

    public void setBookUseLanguage(String bookUseLanguage){
        this.bookUseLanguage = bookUseLanguage;
    }

    public String getBookPoster(){
        return bookPoster;
    }

    public void setBookPoster(String bookPoster){
        this.bookPoster = bookPoster;
    }

    public String getBookSource(){
        return bookSource;
    }

    public void setBookSource(String bookSource){
        this.bookSource = bookSource;
    }

    public String getBookType(){
        return bookType;
    }

    public void setBookType(String bookType){
        this.bookType = bookType;
    }

    public String getBookDescribe(){
        return bookDescribe;
    }

    public void setBookDescribe(String bookDescribe){
        this.bookDescribe = bookDescribe;
    }

    public String getBookTypeCode(){
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode){
        this.bookTypeCode = bookTypeCode;
    }
    
}
