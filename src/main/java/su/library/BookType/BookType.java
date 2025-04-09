package su.library.BookType;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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

    public BookType(String typeName, String typeCode, List<Book> books){
        super();
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.books = books;
    }
    public List<Book> getBooks(){
                  return books;
         }

    
}
