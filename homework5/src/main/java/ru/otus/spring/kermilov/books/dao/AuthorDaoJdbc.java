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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao
{
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author save(Author a) {
        Optional<Author> byName = getByName(a.getName());
        Optional<Author> byID = (a.getId() > 0) ? getByID(a.getId()) : Optional.empty();
        if (!byName.isEmpty()) {
            if (byID.isEmpty() || byID.get().equals(byName.get())) {
                return byName.get();
            }
        }
        Map<String, Object> params = Map.of("id", a.getId(),"name", a.getName());
        if (!byID.isEmpty()) {
            jdbc.update("update AuthorS set name = :name where id = :id", params);
            return a;
        }
        else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update("insert into AuthorS (name) values (:name)", new MapSqlParameterSource(params), keyHolder);
            return new Author(keyHolder.getKey().longValue(), a.getName());
        }
    }

    @Override
    public Optional<Author> getByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select * from AuthorS where id = :id", params, new AuthorMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.of(jdbc.queryForObject("select * from AuthorS where name = :name", params, new AuthorMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from AuthorS where id = :id", params);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("select * from AuthorS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
