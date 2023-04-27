package Library.service;

import Library.dto.common.ValueDto;
import Library.dto.BaseBookDto;
import Library.dto.BookDto;
import Library.dto.BookPatch;
import Library.dto.quary.params.BookFitterOptions;
import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.List;

public interface BookService {
    BookDto create(BaseBookDto bookDto);
    List<BookDto> findAll(BookFitterOptions fitterOptions, Integer limit, Integer offset);
    BookDto find(Long id);
    ValueDto<Integer> count(BookFitterOptions params);
    void update(Long id, BaseBookDto facultyDto);
    void patch(Long id, BookPatch facultyPatch);
    void delete(Long id);
}
