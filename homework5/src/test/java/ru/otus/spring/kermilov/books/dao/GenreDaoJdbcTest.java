package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @Test
    void saveTestCorrect() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(saveGenre).matches(genre -> genre.getId() > 0L && genre.getName() == "Genre 1");
    }

    @Test
    void saveAndGetTestCorrect() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(dao.getByID(saveGenre.getId()).get()).usingRecursiveComparison().isEqualTo(saveGenre);
    }
    
    @Test
    void saveAndGetTestIncorrect() {
        val saveGenre1 = dao.save(new Genre(0L, "Genre 1"));
        val saveGenre2 = dao.save(new Genre(saveGenre1.getId(), "Genre 2"));
        assertThat(dao.getByID(saveGenre2.getId()).get()).usingRecursiveComparison().isNotEqualTo(saveGenre1);
    }

    @Test
    void deleteTest() {
        Genre saveGenre = new Genre(0L, "Genre 1");
        dao.save(saveGenre);
        dao.deleteByID(saveGenre.getId());
        assertThat(dao.getByID(saveGenre.getId()).isEmpty()).isEqualTo(true);
    }
}