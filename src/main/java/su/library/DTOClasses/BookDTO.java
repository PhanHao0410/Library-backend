package su.library.DTOClasses;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class BookDTO {

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

}
