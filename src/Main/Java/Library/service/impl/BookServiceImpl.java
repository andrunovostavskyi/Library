package Library.service.impl;

import lombok.AllArgsConstructor;
import Library.dto.common.ValueDto;
import Library.dto.BaseBookDto;
import Library.dto.BookDto;
import Library.dto.BookPatch;
import Library.dto.quary.params.BookFitterOptions;
import Library.entity.BookEntity;
import Library.exception.BadRequestException;
import Library.mapper.BookMapper;
import Library.repository.BookRepository;
import Library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto create(BaseBookDto facultyDto) {
        BookEntity facultyEntity = bookMapper.toEntity(facultyDto);
        BookEntity createdFacultyEntity = bookRepository.create(facultyEntity);
        return bookMapper.toDto(createdFacultyEntity);
    }

    @Override
    public List<BookDto> findAll(@RequestParam(required = false) BookFitterOptions fitterOptions, @RequestParam(defaultValue = "100", required = false) Integer limit, @RequestParam(defaultValue = "0", required = false) Integer offset) {
        List<BookEntity> booksEntities = bookRepository.findAll(fitterOptions, limit, offset);
        return bookMapper.toDtoList(booksEntities);
    }

    @Override
    public BookDto find(Long id) {
        BookEntity bookEntity = bookRepository.find(id);
        return bookMapper.toDto(bookEntity);
    }

    @Override
    public ValueDto<Integer> count(BookFitterOptions params) {
        int count = bookRepository.count(params);
        return new ValueDto<>(count);
    }

    @Override
    public void update(Long id, BaseBookDto bookDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookDto);
        bookEntity.setId(id);

        bookRepository.update(bookEntity);
    }

    @Override
    public void patch(Long id, BookPatch bookPatch) {
        if (bookPatch.isEmpty()) {
            throw new BadRequestException("Faculty patch is empty!");
        }

        bookRepository.patch(id, bookPatch);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(id);
    }
}
