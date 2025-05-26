package su.library.BookType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import su.library.DTOClasses.BookDTO;
import su.library.DTOClasses.BookStatus;
import su.library.DTOClasses.BookTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("/api/v1")
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    @GetMapping
    public ResponseEntity<List<BookType>> allBookTypes() {
        return new ResponseEntity<List<BookType>>(bookTypeService.getAllBookTypes(),
                HttpStatus.OK);
    }

    @PostMapping("/createNewType")
    public ResponseEntity<BookType> createNewType(@RequestBody BookType bookType) {

        return new ResponseEntity<BookType>(bookTypeService.createNewBookType(bookType), HttpStatus.CREATED);
    }

    @GetMapping("/getType/{typeCode}")
    public ResponseEntity<Optional<BookType>> findByTypeCode(@PathVariable String typeCode) {
        return new ResponseEntity<Optional<BookType>>(bookTypeService.getTypeByTypeCode(typeCode), HttpStatus.OK);
    }

    @GetMapping("/{typeCode}/books")
    public ResponseEntity<List<Book>> getAllBooksByTypeCode(@PathVariable String typeCode) {
        return new ResponseEntity<List<Book>>(bookTypeService.getBooksInTypeCode(typeCode), HttpStatus.OK);
    }

    @GetMapping("/{typeCode}/getSingleBook/{bookId}")
    public ResponseEntity<Book> singleBook(@PathVariable String typeCode, @PathVariable String bookId) {
        return new ResponseEntity<Book>(bookTypeService.getSingleBook(typeCode, bookId), HttpStatus.OK);
    }

    @GetMapping("/{typeCode}/practices")
    public ResponseEntity<List<PracticeDocuments>> getAllPracticeByTypeCode(@PathVariable String typeCode) {
        return new ResponseEntity<List<PracticeDocuments>>(bookTypeService.getPracticesInTypeCode(typeCode),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteType/{typeId}")
    public ResponseEntity<Optional<BookType>> deleteBookType(@PathVariable String typeId) {
        return new ResponseEntity<Optional<BookType>>(bookTypeService.getRemoveByTypeId(typeId), HttpStatus.OK);
    }

    @PostMapping("/{typeCode}/createBook")
    public ResponseEntity<String> createNewBook(@PathVariable String typeCode,
            @RequestPart("createBook") BookDTO createBook, @RequestParam("file") MultipartFile file)
            throws IOException {
        String result = bookTypeService.addBookToBookType(typeCode, createBook, file);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{typeCode}/deleteBook/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String typeCode, @PathVariable String bookId) {
        String result = bookTypeService.deleteBookByBookId(typeCode, bookId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{typeCode}/updateBook/{bookId}")
    public ResponseEntity<String> updateBookByBookId(@PathVariable String typeCode, @PathVariable String bookId,
            @RequestPart("updateBook") BookDTO updateBook, @RequestParam("file") MultipartFile file)
            throws IOException {
        String result = bookTypeService.UpdateBookInformation(typeCode, bookId, updateBook, file);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{typeCode}/createPractice")
    public ResponseEntity<String> createNewPractice(@PathVariable String typeCode,
            @RequestBody PracticeDocuments practice) {
        String result = bookTypeService.addPracticesToBookType(typeCode, practice);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{typeCode}/deletePractice/{practiceId}")
    public ResponseEntity<String> deletePractice(@PathVariable String typeCode, @PathVariable String practiceId) {
        String result = bookTypeService.deletePracticeById(typeCode, practiceId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{typeCode}/updatePractice/{practiceId}")
    public ResponseEntity<String> updatePractice(@PathVariable String typeCode, @PathVariable String practiceId,
            @RequestBody PracticeDocuments updatePractice) {
        String result = bookTypeService.updatePracticeInformation(typeCode, practiceId, updatePractice);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{typeCode}/updateStatus/{bookId}")
    public ResponseEntity<String> updateStatus(@PathVariable String typeCode, @PathVariable String bookId,
            @RequestBody BookStatus updateStatus) {
        String result = bookTypeService.updateStatusBook(typeCode, bookId, updateStatus);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{typeCode}/updateTime/{bookId}")
    public ResponseEntity<String> updateTime(@PathVariable String typeCode, @PathVariable String bookId,
            @RequestBody BookTime updateTime) {
        String result = bookTypeService.updateTimeBook(typeCode, bookId, updateTime);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") String id) {
        try {
            return bookTypeService.downloadFile(id);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error retrieving file: " + e.getMessage());
        }
    }

}
