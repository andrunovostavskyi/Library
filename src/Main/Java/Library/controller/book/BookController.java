package Library.controller.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import Library.dto.common.ValueDto;
import Library.dto.BaseBookDto;
import Library.dto.BookDto;
import Library.dto.BookPatch;
import Library.dto.quary.params.BookFitterOptions;
import Library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BaseBookDto faculty) {
        return bookService.create(faculty);
    }

    @GetMapping
    @Operation(
            parameters = {
                    @Parameter(name = "title"),
                    @Parameter(name = "author") // modify parameter name to "author"
            }
    )
    public List<BookDto> findAll(@Parameter(hidden = true) BookFitterOptions fitterOptions,
                                 @RequestParam(required = false) Integer limit,
                                 @RequestParam(required = false) Integer offset) {
        return bookService.findAll(fitterOptions, limit, offset);
    }

    @GetMapping("{id}")
    public BookDto find(@PathVariable Long id) {
        return bookService.find(id);
    }

    @GetMapping("count")
    @Operation(
            parameters = {
                    @Parameter(name = "title"),
                    @Parameter(name = "autor")
            }
    )
    public ValueDto<Integer> count(@Parameter(hidden = true) BookFitterOptions params) {
        return bookService.count(params);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody BaseBookDto faculty) {
        bookService.update(id, faculty);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patch(@PathVariable Long id, @RequestBody BookPatch facultyPatch) {
        bookService.patch(id, facultyPatch);
    }

    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
