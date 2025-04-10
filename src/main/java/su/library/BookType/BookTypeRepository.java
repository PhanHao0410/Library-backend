package su.library.BookType;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTypeRepository extends MongoRepository<BookType, String> {
    // Optional<BookType> findTypeByTypeId(String typeId);
    Optional<BookType> deleteTypeByTypeId(String typeId);

    Optional<BookType> findTypeByTypeCode(String typeCode);
}
