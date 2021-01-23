package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorDaoJpa.class})
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDaoJpa dao;
    @Autowired
    private TestEntityManager em;

    @Test
    void saveShouldInsert() {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(saveAuthor).matches(Author -> Author.getId() > 0L && Author.getName().equals("Author 1"));
    }

    @Test
    void getShouldGet() {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(dao.getByID(saveAuthor.getId()).get()).usingRecursiveComparison().isEqualTo(saveAuthor);
    }

    @Test
    void saveShouldUpdateById() {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        val saveAuthor = dao.save(new Author(id, "Author 2"));
        assertThat(dao.getByID(id).get().getName()).isEqualTo("Author 2");
    }

    @Test
    void deleteShouldDelete() {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        dao.deleteByID(id);
        em.clear();
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}