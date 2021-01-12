package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import ru.otus.spring.kermilov.books.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @Test
    void saveShouldInsert() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(saveGenre).matches(genre -> genre.getId() > 0L && genre.getName().equals("Genre 1"));
    }

    @Test
    void getShouldGet() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(dao.getByID(saveGenre.getId()).get()).usingRecursiveComparison().isEqualTo(saveGenre);
    }
    
    @Test
    void saveShouldUpdate() {
        val id = dao.save(new Genre(0L, "Genre 1")).getId();
        val saveGenre = dao.save(new Genre(id, "Genre 2"));
        assertThat(dao.getByID(id).get().getName()).isEqualTo("Genre 2");
    }

    @Test
    void saveNotShouldUpdateExistName() {
        val id = dao.save(new Genre(0L, "Genre 1")).getId();
        dao.save(new Genre(0L, "Genre 2"));
        assertThrows(DuplicateKeyException.class, () -> {dao.save(new Genre(id, "Genre 2"));});
    }

    @Test
    void deleteShouldDelete() {
        val id = dao.save(new Genre(0L, "Genre 1")).getId();
        dao.deleteByID(id);
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}