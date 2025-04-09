package su.library.BookType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.stereotype.Service;

@Service
public class BookTypeService {


    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Autowired 
    private MongoTemplate mongoTemplate;


    


    public List<BookType> getAllBookTypes(){
        return bookTypeRepository.findAll();
    }
   
    public BookType createNewBookType(BookType bookType){
        return bookTypeRepository.save(bookType);
    }

    public Optional<BookType> getRemoveByTypeId(String typeId){
        return bookTypeRepository.deleteTypeByTypeId(typeId);
    }

    public Optional<BookType> getTypeByTypeCode (String typeCode){
        return bookTypeRepository.findTypeByTypeCode(typeCode);
    }

    public String deleteBookByBookId(String typeCode, String bookId) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode));
        
        Update update = new Update().pull("books", Query.query(Criteria.where("bookId").is(bookId)));
    
        mongoTemplate.updateFirst(query, update, BookType.class);
        return "Book deleted success!!";
    }

    public String UpdateBookInformation(String typeCode, String bookId, Book updateBook) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode)
        .andOperator(
            new Criteria().orOperator(
                Criteria.where("books.bookName").is(updateBook.getBookName()),
                Criteria.where("books.bookAuthor").is(updateBook.getBookAuthor())
            )
        )
    );
    boolean exists = mongoTemplate.exists(query, BookType.class);
    if (exists) {
        throw new LibraryExceptionHandler("This Book of Author already exists!");
    }
        Query updateQuery = new Query(Criteria.where("typeCode").is(typeCode)
            .and("books.bookId").is(bookId));
        Update update = new Update()
        .set("books.$.bookName", updateBook.getBookName())
        .set("books.$.bookAuthor", updateBook.getBookAuthor())
        .set("books.$.bookUseLanguage", updateBook.getBookUseLanguage())
        .set("books.$.bookDescribe", updateBook.getBookDescribe());
    
        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Update Success!";
    }
    

    public String addBookToBookType(String typeCode, Book createBook) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode)
        .andOperator(
            new Criteria().orOperator(
                Criteria.where("books.bookName").is(createBook.getBookName()),
                Criteria.where("books.bookAuthor").is(createBook.getBookAuthor())
            )
        )
    );

    boolean exists = mongoTemplate.exists(query, BookType.class);
    if (exists) {
        throw new LibraryExceptionHandler("Book already exists");
    }
        String hobbyId = UUID.randomUUID().toString();
        Book newBook = new Book(hobbyId, createBook);

        Query createQuery = new Query(Criteria.where("typeCode").is(typeCode));
        Update update = new Update().push("books", newBook); 
        mongoTemplate.updateFirst(createQuery, update, BookType.class);
        return "Book created successfully!";
    }
   
}
