package su.library.BookType;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "booktypes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookType {

    @Id

    private String typeId;

    private String typeName;

    private String typeCode;

    private List<Book> books;

    private List<PracticeDocuments> practices;

    public BookType(String typeName, String typeCode, List<Book> books, List<PracticeDocuments> practices) {
        super();
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.books = books;
        this.practices = practices;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<PracticeDocuments> getPractices() {
        return practices;
    }

}
