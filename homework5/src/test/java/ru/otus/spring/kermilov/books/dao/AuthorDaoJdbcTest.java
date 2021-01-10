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
    void saveTestCorrect() throws Exception {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(saveAuthor).matches(Author -> Author.getId() > 0L && Author.getName().equals("Author 1"));
    }

    @Test
    void saveAndGetTestCorrect() throws Exception {
        val saveAuthor = dao.save(new Author(0L, "Author 1"));
        assertThat(dao.getByID(saveAuthor.getId()).get()).usingRecursiveComparison().isEqualTo(saveAuthor);
    }

    @Test
    void saveAndGetTestIncorrect() throws Exception {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        val saveAuthor = dao.save(new Author(id, "Author 2"));
        assertThat(dao.getByID(id).get().getName()).isEqualTo("Author 2");
    }

    @Test
    void deleteTest() throws Exception {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        dao.deleteByID(id);
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}