package su.library.BookType;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @DeleteMapping("/deleteType/{typeId}")
    public ResponseEntity<Optional<BookType>> deleteBookType(@PathVariable String typeId) {
        return new ResponseEntity<Optional<BookType>>(bookTypeService.getRemoveByTypeId(typeId), HttpStatus.OK);
    }

    @PostMapping("/{typeCode}/createBook")
    public ResponseEntity<String> createNewBook(@PathVariable String typeCode, @RequestBody Book book) {
        String result = bookTypeService.addBookToBookType(typeCode, book);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{typeCode}/deleteBook/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable String typeCode, @PathVariable String bookId) {
        String result = bookTypeService.deleteBookByBookId(typeCode, bookId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{typeCode}/updateBook/{bookId}")
    public ResponseEntity<String> updateBookByBookId(@PathVariable String typeCode, @PathVariable String bookId,
            @RequestBody Book updateBook) {
        String result = bookTypeService.UpdateBookInformation(typeCode, bookId, updateBook);
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
}
