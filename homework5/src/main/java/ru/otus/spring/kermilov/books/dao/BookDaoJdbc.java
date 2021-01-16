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
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao
{
    private final NamedParameterJdbcOperations jdbc;
    private final GenreDao genreDao;

    @Override
    public Book save(Book a) {
        Optional<Book> byName = getByName(a.getName());
        Optional<Book> byID = (a.getId() > 0) ? getByID(a.getId()) : Optional.empty();
        if (!byName.isEmpty()) {
            if (byID.isEmpty() || byID.get().equals(byName.get())) {
                return byName.get();
            }
        }
        Map<String, Object> params = Map.of("id", a.getId(),"name", a.getName(), "author_id", a.getAuthor().getId(), "genre_id", a.getGenre().getId());
        if (!byID.isEmpty()) {
            jdbc.update("update BookS set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
            return a;
        }
        else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update("insert into BookS (name, author_id, genre_id) values (:name, :author_id, :genre_id)", new MapSqlParameterSource(params), keyHolder);
            return new Book(keyHolder.getKey().longValue(), a.getName(), a.getAuthor(), a.getGenre());
        }    
    }

    @Override
    public Optional<Book> getByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select b.id, b.name, b.author_id, a.name as author_name, genre_id " +
                                                          "from BookS b " +
                                                          "join AuthorS a " +
                                                            "on a.id = b.author_id " +
                                                         "where b.id = :id", params, new BookMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.of(jdbc.queryForObject("select b.id, b.name, b.author_id, a.name as author_name, genre_id " +
                                                          "from BookS b " +
                                                          "join AuthorS a " +
                                                            "on a.id = b.author_id " +
                                                         "where b.name = :name", params, new BookMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from BookS where id = :id", params);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("select b.id, b.name, b.author_id, a.name as author_name, genre_id " +
                                 "from BookS b " +
                                 "join AuthorS a " +
                                   "on a.id = b.author_id ", new BookMapper());
    }

    private class BookMapper implements RowMapper<Book> {
        private Map<Long, Genre> genreList = genreDao.findAll().stream().collect(Collectors.toMap(Genre::getId, c -> c));

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(resultSet.getLong("author_id"),
                                       resultSet.getString("author_name"));
            Genre genre = genreList.get(resultSet.getLong("genre_id"));
            return new Book(id, name, author, genre);
        }
    }
}