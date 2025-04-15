package su.library.BookType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BookType> getAllBookTypes() {
        return bookTypeRepository.findAll();
    }

    public List<Book> getBooksInTypeCode(String typeCode) {
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode));

        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);
        return bookType.getBooks();
    }

    public BookType createNewBookType(BookType bookType) {
        return bookTypeRepository.save(bookType);
    }

    public Optional<BookType> getRemoveByTypeId(String typeId) {
        return bookTypeRepository.deleteTypeByTypeId(typeId);
    }

    public Optional<BookType> getTypeByTypeCode(String typeCode) {
        return bookTypeRepository.findTypeByTypeCode(typeCode);
    }

    public String deleteBookByBookId(String typeCode, String bookId) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode));

        Update update = new Update().pull("books", Query.query(Criteria.where("bookId").is(bookId)));

        mongoTemplate.updateFirst(query, update, BookType.class);
        return "Book deleted success!!";
    }

    public String UpdateBookInformation(String typeCode, String bookId, Book updateBook) {
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode)
                .and("books.bookId").is(bookId));
        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);

        if (bookType == null) {
            throw new LibraryExceptionHandler("BookType or Book not found!");
        }

        boolean isDuplicate = bookType.getBooks().stream()
                .filter(book -> !book.getBookId().equals(bookId))
                .anyMatch(book -> book.getBookName().equals(updateBook.getBookName()) &&
                        book.getBookAuthor().equals(updateBook.getBookAuthor()));

        if (isDuplicate) {
            throw new LibraryExceptionHandler("This Book of Author already exists!");
        }

        Query updateQuery = new Query(Criteria.where("typeCode").is(typeCode)
                .and("books.bookId").is(bookId));
        Update update = new Update()
                .set("books.$.bookName", updateBook.getBookName())
                .set("books.$.bookAuthor", updateBook.getBookAuthor())
                .set("books.$.bookUseLanguage", updateBook.getBookUseLanguage())
                .set("books.$.bookDescribe", updateBook.getBookDescribe())
                .set("books.$.bookType", updateBook.getBookType())
                .set("books.$.bookSource", updateBook.getBookSource())
                .set("books.$.bookPoster", updateBook.getBookPoster());

        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Book updated Success!";
    }

    public String addBookToBookType(String typeCode, Book createBook) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode)
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("books.bookName").is(createBook.getBookName()),
                                Criteria.where("books.bookAuthor").is(createBook.getBookAuthor()))));

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

    public String addPracticesToBookType(String typeCode, PracticeDocuments createPractice) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode)
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("practices.practiceName")
                                        .is(createPractice.getPracticeName()),
                                Criteria.where("practices.practiceName")
                                        .is(createPractice.getPracticeLink()))));

        boolean exists = mongoTemplate.exists(query, BookType.class);
        if (exists) {
            throw new LibraryExceptionHandler("Practice document already exists");
        }
        String hobbyId = UUID.randomUUID().toString();
        PracticeDocuments newPractice = new PracticeDocuments(hobbyId, createPractice);

        Query createQuery = new Query(Criteria.where("typeCode").is(typeCode));
        Update update = new Update().push("practices", newPractice);
        mongoTemplate.updateFirst(createQuery, update, BookType.class);
        return "Practice document created successfully!";
    };

    public String deletePracticeById(String typeCode, String practiceId) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode));

        Update update = new Update().pull("practices",
                Query.query(Criteria.where("practiceId").is(practiceId)));

        mongoTemplate.updateFirst(query, update, BookType.class);
        return "Practices deleted success!!";
    }

    public String updatePracticeInformation(String typeCode, String practiceId, PracticeDocuments updatePractice) {
        Query query = new Query(Criteria.where("typeCode").is(typeCode)
                .andOperator(new Criteria().orOperator(
                        Criteria.where("practices.practiceName").is(updatePractice.getPracticeName()),
                        Criteria.where("practices.practiceLink").is(updatePractice.getPracticeLink()))));

        boolean exists = mongoTemplate.exists(query, BookType.class);

        if (exists) {
            throw new LibraryExceptionHandler("This Practice document already exists!");
        }

        Query updateQuery = new Query(
                Criteria.where("typeCode").is(typeCode).and("practices.practiceId").is(practiceId));

        Update update = new Update()
                .set("practices.$.practiceName", updatePractice.getPracticeName())
                .set("practices.$.practiceLink", updatePractice.getPracticeLink())
                .set("practices.$.practiceDescribe", updatePractice.getPracticeDescribe());
        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Practice updated success!";

    }

}
