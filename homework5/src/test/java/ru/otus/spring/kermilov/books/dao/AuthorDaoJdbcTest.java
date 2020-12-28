package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    @Test
    void saveTestCorrect() {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(saveAuthor).matches(Author -> Author.getId() > 0L && Author.getName().equals("Author 1"));
    }

    @Test
    void saveAndGetTestCorrect() {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(dao.getByID(saveAuthor.getId()).get()).usingRecursiveComparison().isEqualTo(saveAuthor);
    }

    @Test
    void saveAndGetTestIncorrect() {
        val saveAuthor1 = dao.save(new Author(0L, "Author 1"));
        val saveAuthor2 = dao.save(new Author(saveAuthor1.getId(), "Author 2"));
        assertThat(dao.getByID(saveAuthor2.getId()).get()).usingRecursiveComparison().isNotEqualTo(saveAuthor1);
    }

    @Test
    void deleteTest() {
        Author saveAuthor = new Author(0L, "Author 1");
        dao.save(saveAuthor);
        dao.deleteByID(saveAuthor.getId());
        assertThat(dao.getByID(saveAuthor.getId()).isEmpty()).isEqualTo(true);
    }
}