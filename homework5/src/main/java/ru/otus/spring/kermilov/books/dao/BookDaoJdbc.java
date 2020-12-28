package ru.otus.spring.kermilov.books.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao
{
    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book save(Book a) throws Exception {
        if (authorDao.getByID(a.getAuthor().getId()).isEmpty()) {
            throw new Exception("Incorrect author");
        }
        if (genreDao.getByID(a.getGenre().getId()).isEmpty()) {
            throw new Exception("Incorrect genre");
        }
        Optional<Book> res = getByName(a.getName());
        if (!res.isEmpty()) {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", res.get().getId(),"author_id", a.getAuthor().getId(), "genre_id", a.getGenre().getId()));
            jdbc.update("update BookS set author_id = :author_id, genre_id = :genre_id where id = :id", params);
            return new Book(res.get().getId(), a.getName(), a.getAuthor(), a.getGenre());
        }
        else {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", a.getName(),"author_id", a.getAuthor().getId(), "genre_id", a.getGenre().getId()));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update("insert into BookS (name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, keyHolder);
            return new Book(keyHolder.getKey().longValue(), a.getName(), a.getAuthor(), a.getGenre());
        }
    }

    @Override
    public Optional<Book> getByID(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select * from BookS where id = :id", params, new BookMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.of(jdbc.queryForObject("select * from BookS where name = :name", params, new BookMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByID(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from BookS where id = :id", params);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select * from BookS", new BookMapper());
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = authorDao.getByID(resultSet.getLong("author_id")).get();
            Genre genre = genreDao.getByID(resultSet.getLong("genre_id")).get();
            return new Book(id, name, author, genre);
        }
    }
}