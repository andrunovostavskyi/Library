package Library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class BookPatch {
    private String title;
    private String author;
    private String publisher;
    private String genre;
    private String language;
    private String synopsis;
    private Boolean isAvailable;
    private Integer publication_year;
    private Integer num_page;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean empty = true;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isTitleUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isAuthorUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isPublisherUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isGenreUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isLanguageUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isSynopsisUpdated;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isAvailableUpdated;
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isPublication_yearUpdate;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private boolean isNum_pageUpdate;

    public void setTitle(String title) {
        empty = false;
        isTitleUpdated = true;

        this.title = title;
    }

    public void setAuthor(String author) {
        empty = false;
        isAuthorUpdated = true;

        this.author = author;
    }

    public void setPublisher(String publisher) {
        empty = false;
        isPublisherUpdated = true;

        this.publisher = publisher;
    }

    public void setGenre(String genre) {
        empty = false;
        isGenreUpdated = true;

        this.genre = genre;
    }

    public void setLanguage(String language) {
        empty = false;
        isLanguageUpdated = true;

        this.language = language;
    }

    public void setSynopsis(String synopsis) {
        empty = false;
        isSynopsisUpdated = true;

        this.synopsis = synopsis;
    }
}
