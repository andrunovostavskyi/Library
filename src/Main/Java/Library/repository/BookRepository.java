package Library.repository;

import Library.dto.BookPatch;
import Library.dto.quary.params.BookFitterOptions;
import Library.entity.BookEntity;

import java.util.List;

public interface BookRepository {
    BookEntity create(BookEntity faculty);
    List<BookEntity> findAll(BookFitterOptions fitterOptions, Integer limit, Integer offset);
    BookEntity find(Long id);
    int count(BookFitterOptions params);
    void update(BookEntity faculty);
    void patch(Long id, BookPatch facultyPatch);
    void delete(Long id);
}
