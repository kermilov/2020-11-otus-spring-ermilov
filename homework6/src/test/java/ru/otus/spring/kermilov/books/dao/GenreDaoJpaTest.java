package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({GenreDaoJpa.class})
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa dao;
    @Autowired
    private TestEntityManager em;

    @Test
    void saveShouldInsert() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(saveGenre).matches(Genre -> Genre.getId() > 0L && Genre.getName().equals("Genre 1"));
    }

    @Test
    void getShouldGet() {
        val saveGenre = dao.save(new Genre(0L, "Genre 1"));
        assertThat(dao.getByID(saveGenre.getId()).get()).usingRecursiveComparison().isEqualTo(saveGenre);
    }

    @Test
    void saveShouldUpdateById() {
        val id = dao.save(new Genre(0L, "Genre 1")).getId();
        val saveGenre = dao.save(new Genre(id, "Genre 2"));
        assertThat(dao.getByID(id).get().getName()).isEqualTo("Genre 2");
    }

    @Test
    void deleteShouldDelete() {
        val id = dao.save(new Genre(0L, "Genre 1")).getId();
        dao.deleteByID(id);
        em.clear();
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}