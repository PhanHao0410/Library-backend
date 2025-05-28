package su.library.BookType;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.model.GridFSFile;

import su.library.DTOClasses.BookDTO;
import su.library.DTOClasses.BookStatus;
import su.library.DTOClasses.BookTime;
import su.library.exception.AlreadyExistsException;
import su.library.exception.LibraryExceptionHandler;
import su.library.exception.NoFoundException;
import su.library.exception.SizeFileUploadException;

@Service
public class BookTypeService {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    public List<BookType> getAllBookTypes() {
        return bookTypeRepository.findAll();
    }

    public List<Book> getBooksInTypeCode(String typeCode) {
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode));

        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);
        return bookType.getBooks();
    }

    public Book getSingleBook(String typeCode, String bookId) {
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode));

        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);

        for (Book book : bookType.getBooks()) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public List<PracticeDocuments> getPracticesInTypeCode(String typeCode) {
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode));

        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);
        return bookType.getPractices();
    }

    public String createNewBookType(BookType bookType) {
        if (bookTypeRepository.findTypeByTypeName(bookType.getTypeName()).isPresent()
                || bookTypeRepository.findTypeByTypeCode(bookType.getTypeCode()).isPresent()) {
            throw new AlreadyExistsException("Type book already exists.");
        }
        ;

        bookTypeRepository.save(bookType);
        return "Create new type success";
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

    public String UpdateBookInformation(String typeCode, String bookId, BookDTO updateBook, MultipartFile file)
            throws IOException {

        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode)
                .and("books.bookId").is(bookId));
        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);

        if (bookType == null) {
            throw new NoFoundException("BookType or Book not found!");
        }

        boolean isDuplicate = bookType.getBooks().stream()
                .filter(book -> !book.getBookId().equals(bookId))
                .anyMatch(book -> book.getBookName().equals(updateBook.getBookName()) &&
                        book.getBookAuthor().equals(updateBook.getBookAuthor()));

        if (isDuplicate) {
            throw new AlreadyExistsException("This Book of Author already exists!");
        }

        String currentFileId = null;
        for (Book book : bookType.getBooks()) {
            if (book.getBookId().equals(bookId)) {
                currentFileId = book.getBookFileId();
                break;
            }
        }
        if (file != null && !file.isEmpty()) {
            long maxSize = 35 * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new SizeFileUploadException("File size exceeds the maximum allowed size of 35MB.");
            }
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
                .set("books.$.bookPoster", updateBook.getBookPoster())
                .set("books.$.bookStatus", updateBook.getBookStatus())
                .set("books.$.bookStatusCode", updateBook.getBookStatusCode())
                .set("books.$.expectedTime", updateBook.getExpectedTime())
                .set("books.$.spentTime", updateBook.getSpentTime());
        if (file != null && !file.isEmpty()) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(currentFileId)));
            ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType());
            update.set("books.$.bookFileId", fileId);
        } else {
            update.set("books.$.bookFileId", currentFileId);
        }

        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Book updated Success!";
    }

    public String addBookToBookType(String typeCode, BookDTO createBook, MultipartFile file) throws IOException {

        Query query = new Query(Criteria.where("typeCode").is(typeCode)
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("books.bookName").is(createBook.getBookName()),
                                Criteria.where("books.bookAuthor").is(createBook.getBookAuthor()))));

        boolean exists = mongoTemplate.exists(query, BookType.class);
        if (exists) {
            throw new AlreadyExistsException("Book already exists");
        }

        if (file != null && !file.isEmpty()) {
            long maxSize = 35 * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new SizeFileUploadException("File size exceeds the maximum allowed size of 35MB.");
            }
        } else {
            throw new NoFoundException("The PDF book file is empty. Please upload a new file.");
        }
        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(),
                file.getContentType());

        String hobbyId = UUID.randomUUID().toString();
        Book newBook = new Book(hobbyId, createBook, fileId);

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
            throw new AlreadyExistsException("Practice document already exists");
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
        Query findQuery = new Query(Criteria.where("typeCode").is(typeCode)
                .and("practices.practiceId").is(practiceId));

        BookType bookType = mongoTemplate.findOne(findQuery, BookType.class);
        if (bookType == null) {
            throw new NoFoundException("BookType or Book not found!");
        }

        boolean isDuplicate = bookType.getPractices().stream()
                .filter(practice -> !practice.getPracticeId().equals(practiceId))
                .anyMatch(practice -> practice.getPracticeName().equals(updatePractice.getPracticeName())
                        || practice.getPracticeLink().equals(updatePractice.getPracticeLink()));

        if (isDuplicate) {
            throw new AlreadyExistsException("This practice already exists!");
        }

        Query updateQuery = new Query(
                Criteria.where("typeCode").is(typeCode).and("practices.practiceId").is(practiceId));

        Update update = new Update()
                .set("practices.$.practiceName", updatePractice.getPracticeName())
                .set("practices.$.practiceLink", updatePractice.getPracticeLink())
                .set("practices.$.practiceDescribe", updatePractice.getPracticeDescribe())
                .set("practices.$.practicePoster", updatePractice.getPracticePoster());
        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Practice updated success!";

    }

    public String updateStatusBook(String typeCode, String bookId, BookStatus updateStatus) {
        Query updateQuery = new Query(Criteria.where("typeCode").is(typeCode).and("books.bookId").is(bookId));

        Update update = new Update()
                .set("books.$.bookStatusCode", updateStatus.getBookStatusCode())
                .set("books.$.bookStatus", updateStatus.getBookStatus());
        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Status book updated success!";
    }

    public String updateTimeBook(String typeCode, String bookId, BookTime updateTime) {
        Query updateQuery = new Query(Criteria.where("typeCode").is(typeCode).and("books.bookId").is(bookId));

        Update update = new Update()
                .set("books.$.expectedTime", updateTime.getExpectedTime())
                .set("books.$.spentTime", updateTime.getSpentTime());
        mongoTemplate.updateFirst(updateQuery, update, BookType.class);
        return "Time read book updated success!";
    }

    public ResponseEntity<?> downloadFile(String fileId) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        GridFsResource resource = gridFsTemplate.getResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
