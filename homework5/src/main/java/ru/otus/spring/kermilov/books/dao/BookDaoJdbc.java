package ru.otus.spring.kermilov.books.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Book;

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
        if (authorDao.getByID(a.getAuthorID()).isEmpty()) {
            throw new Exception("Incorrect author_id");
        }
        if (genreDao.getByID(a.getGenreID()).isEmpty()) {
            throw new Exception("Incorrect genre_id");
        }
        Optional<Book> res = getByName(a.getName());
        if (!res.isEmpty()) {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of("id", res.get().getId(),"author_id", a.getAuthorID(), "genre_id", a.getGenreID()));
            jdbc.update("update BookS set author_id = :author_id, genre_id = :genre_id where id = :id", params);
            return new Book(res.get().getId(), a.getName(), a.getAuthorID(), a.getGenreID());
        }
        else {
            MapSqlParameterSource params = new MapSqlParameterSource(Map.of("name", a.getName(),"author_id", a.getAuthorID(), "genre_id", a.getGenreID()));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update("insert into BookS (name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, keyHolder);
            return new Book(keyHolder.getKey().longValue(), a.getName(), a.getAuthorID(), a.getGenreID());
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

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long author_id = resultSet.getLong("author_id");
            long genre_id = resultSet.getLong("genre_id");
            return new Book(id, name, author_id, genre_id);
        }
    }
}