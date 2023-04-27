package Library.entity;
import lombok.Data;

@Data
public class BookEntity {
    private Long id;
    private Long num_pages;
    private Long publication_year;
    private String title;
    private String author;
    private String publisher;
    private String genre;
    private String language;
    private String synopsis ;
    private Boolean isAvailable;

}

