CREATE TABLE books (
                       id BIGINT PRIMARY KEY,
                       num_pages BIGINT,
                       publication_year BIGINT,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publisher VARCHAR(255),
                       genre VARCHAR(255),
                       language VARCHAR(255),
                       synopsis TEXT,
                       is_available BOOLEAN
);
