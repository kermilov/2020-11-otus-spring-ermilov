package ru.otus.spring.kermilov.books.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao
{
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre save(Genre a) throws Exception {
        Optional<Genre> byName = getByName(a.getName());
        Optional<Genre> byID = (a.getId() > 0) ? getByID(a.getId()) : Optional.empty();
        if (!byName.isEmpty()) {
            if (byID.isEmpty() || byID.get().equals(byName.get())) {
                return byName.get();
            } else {
                throw new Exception("Can't save cause here another Genre with this name");
            }
        }
        Map<String, Object> params = Map.of("id", a.getId(),"name", a.getName());
        if (!byID.isEmpty()) {
            jdbc.update("update GenreS set name = :name where id = :id", params);
            return a;
        }
        else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update("insert into GenreS (name) values (:name)", new MapSqlParameterSource(params), keyHolder);
            return new Genre(keyHolder.getKey().longValue(), a.getName());
        }
    }

    @Override
    public Optional<Genre> getByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.of(jdbc.queryForObject("select * from GenreS where id = :id", params, new GenreMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return Optional.of(jdbc.queryForObject("select * from GenreS where name = :name", params, new GenreMapper()));
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByID(long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from GenreS where id = :id", params);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select * from GenreS", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
