package Library.mapper;

import Library.dto.BaseBookDto;
import Library.dto.BookDto;
import Library.entity.BookEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookEntity toEntity(BaseBookDto baseBookDto);
    BookDto toDto(BookEntity bookEntity);
    List<BookDto> toDtoList(List<BookEntity> bookEntities);
}
