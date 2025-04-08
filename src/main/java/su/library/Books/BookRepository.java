package su.library.Books;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface BookRepository extends MongoRepository<Book, String>{
    Optional<Book> findBookByBookId(String bookId);
    Optional<Book> deleteBookByBookId(String bookId);

}
