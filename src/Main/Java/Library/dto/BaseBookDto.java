package Library.dto;
import lombok.Data;

@Data
public class BaseBookDto {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String synopsis;
    private Integer num_pages;
    private String language;
    private Integer publication_year;
    private Boolean is_available;
}
