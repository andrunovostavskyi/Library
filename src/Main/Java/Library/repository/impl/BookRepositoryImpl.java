package Library.repository.impl;

import Library.dto.BookPatch;
import lombok.AllArgsConstructor;
import Library.dto.quary.params.BookFitterOptions;
import Library.entity.BookEntity;
import Library.exception.DataConflictException;
import Library.exception.NotFoundException;
import Library.repository.BookRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String INSERT_BOOK_QUERY = """
    INSERT INTO books (
        id,
        title,
        author,
        publisher,
        publication_year,
        num_pages,
        genre,
        language,
        synopsis,
        is_available
    ) VALUES (
        :id,
        :title,
        :author,
        :publisher,
        :publication_year,
        :num_pages,
        :genre,
        :language,
        :synopsis,
        :is_available
    )
""";

    private static final String SELECT_BOOKS_QUERY = """
            SELECT
                id,
                title,
                author,
                publisher,
                publication_year,
                num_pages,
                genre,
                language,
                synopsis,
                is_available
            FROM books
            """;

    private static final String SELECT_BOOK_BY_ID_QUERY = """
        SELECT
                id,
                title,
                author,
                publisher,
                publication_year,
                num_pages,
                genre,
                language,
                synopsis,
                is_available
        FROM books
        WHERE id = :id
        """;




    private static final String UPDATE_BOOK_BY_ID_QUERY = """
            UPDATE books SET
                title=:title,
                author=:author,
                publisher=:publisher,
                publication_year=:publication_year,
                num_pages=:num_pages,
                genre=:genre,
                language=:language,
                synopsis=:synopsis,
                is_available=:is_available
            WHERE id = :id
            """;

    private static final String SELECT_BOOK_COUNT_QUERY = "SELECT COUNT(1) FROM books";

    private static final String PATCH_BOOK_BY_ID_QUERY_TEMPLATE = """
            UPDATE books SET
                %s
            WHERE id = :id
            """;

    private static final String DELETE_BOOK_BY_ID_QUERY = """
            DELETE FROM books WHERE id = :id
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<BookEntity> BOOK_ROW_MAPPER = (rs, rowNum) -> {
        BookEntity entity = new BookEntity();

        entity.setId(rs.getObject("id", Long.class));
        entity.setAuthor(rs.getString("author"));
        entity.setTitle(rs.getString("title"));
        entity.setPublisher(rs.getString("publisher"));
        entity.setPublication_year(rs.getLong("publication_year"));
        entity.setNum_pages(rs.getLong("num_pages"));
        entity.setGenre(rs.getString("genre"));
        entity.setLanguage(rs.getString("language"));
        entity.setSynopsis(rs.getString("synopsis"));
        entity.setIsAvailable(rs.getBoolean("is_available"));

        return entity;
    };





    @Override
    public BookEntity create(BookEntity book) {
        SqlParameterSource sqlParameters = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("num_pages", book.getNum_pages())
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("genre", book.getGenre())
                .addValue("publication_year", book.getPublication_year())
                .addValue("publisher", book.getPublisher())
                .addValue("is_available", book.getIsAvailable())
                .addValue("synopsis", book.getSynopsis())
                .addValue("language", book.getLanguage());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(INSERT_BOOK_QUERY, sqlParameters, keyHolder);
        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Faculty with name \"%s\" already exists!", book.getTitle()));
            }

            throw e;
        }

        Long id = (Long) keyHolder.getKeys().get("id");
        book.setId(id);

        return book;
    }

    @Override
    public List<BookEntity> findAll(BookFitterOptions fitterOptions, Integer limit, Integer offset) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_BOOKS_QUERY);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        appendConditions(queryBuilder, parameters, fitterOptions);

        if (limit != null) {
            queryBuilder.append(" LIMIT :limit");
            parameters.addValue("limit", limit);
        }

        if (offset != null && offset != 0) {
            queryBuilder.append(" OFFSET :offset");
            parameters.addValue("offset", offset);
        }

        String query = queryBuilder.toString();

        return jdbcTemplate.query(query, parameters, BOOK_ROW_MAPPER);
    }

    @Override
    public BookEntity find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID_QUERY, new MapSqlParameterSource("id", id), BOOK_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Faculty with id " + id + " not found!");
        }
    }

    @Override
    public int count(BookFitterOptions params) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_BOOK_COUNT_QUERY);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        appendConditions(queryBuilder, parameters, params);

        String query = queryBuilder.toString();

        return jdbcTemplate.queryForObject(query, parameters, Integer.class);
    }

    @Override
    public void update(BookEntity book) {
        int affectedRows;
        try {
            affectedRows = jdbcTemplate.update(UPDATE_BOOK_BY_ID_QUERY, new MapSqlParameterSource()
                    .addValue("id", book.getId())
                    .addValue("num_pages", book.getNum_pages())
                    .addValue("title", book.getTitle())
                    .addValue("author", book.getAuthor())
                    .addValue("genre", book.getGenre())
                    .addValue("publication_year", book.getPublication_year())
                    .addValue("publisher", book.getPublisher())
                    .addValue("is_available", book.getIsAvailable())
                    .addValue("synopsis", book.getSynopsis())
                    .addValue("language", book.getLanguage()));

        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Faculty with name \"%s\" already exists!", book.getTitle()));
            }

            throw e;
        }

        if (affectedRows == 0) {
            throw new NotFoundException("Faculty with id " + book.getId() + " not found!");
        }
    }

    @Override
    public void patch(Long id, BookPatch bookPatch) {
        List<String> assignments = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);

        if (bookPatch.isTitleUpdated()) {
            assignments.add("title = :title");
            parameters.addValue("title", bookPatch.getTitle());
        }

        if (bookPatch.isPublisherUpdated()) {
            assignments.add("publisher = :publisher");
            parameters.addValue("publisher", bookPatch.getPublisher());
        }

        if (bookPatch.isAuthorUpdated()) {
            assignments.add("author = :author");
            parameters.addValue("author", bookPatch.getAuthor());
        }

        if (bookPatch.isGenreUpdated()) {
            assignments.add("genre = :genre");
            parameters.addValue("genre", bookPatch.getGenre());
        }

        if (bookPatch.isSynopsisUpdated()) {
            assignments.add("synopsis = :synopsis");
            parameters.addValue("synopsis", bookPatch.getSynopsis());
        }

        if (bookPatch.isLanguageUpdated()) {
            assignments.add("language = :language");
            parameters.addValue("language", bookPatch.getLanguage());
        }

        String assignmentStr = String.join(", ", assignments);
        String query = String.format(PATCH_BOOK_BY_ID_QUERY_TEMPLATE, assignmentStr);

        int affectedRows;

        try {
            affectedRows = jdbcTemplate.update(query, parameters);
        } catch (DuplicateKeyException e) {
            if (e.getCause().getMessage().contains("duplicate key value violates unique constraint \"faculties_name_key\"")) {
                throw new DataConflictException(String.format("Faculty with name \"%s\" already exists!", bookPatch.getTitle()));
            }

            throw e;
        }

        if (affectedRows == 0) {
            throw new NotFoundException("Faculty with id " + id + " not found!");
        }
    }


    @Override
    public void delete(Long id) {
        int affectedRows = jdbcTemplate.update(DELETE_BOOK_BY_ID_QUERY, new MapSqlParameterSource("id", id));

        if (affectedRows == 0) {
            throw new NotFoundException("Faculty with id " + id + " not found!");
        }
    }

    private void appendConditions(StringBuilder queryBuilder, MapSqlParameterSource parameters, BookFitterOptions params) {
        List<String> conditions = new ArrayList<>();

        if (params != null) {
            String nameParam = params.getTitle();
            if (nameParam != null) {
                conditions.add("title LIKE(:title)");
                parameters.addValue("title", "%" + nameParam + "%");
            }

            String infoParam = params.getAuthor();
            if (infoParam != null) {
                conditions.add("author LIKE(:author)");
                parameters.addValue("author", "%" + infoParam+ "%");
            }
        }

        if (!conditions.isEmpty()) {
            String conditionStr = String.join(" AND ", conditions);

            queryBuilder.append(" WHERE ");
            queryBuilder.append(conditionStr);
        }
    }
}
