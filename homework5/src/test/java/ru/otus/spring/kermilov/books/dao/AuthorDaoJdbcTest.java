package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import ru.otus.spring.kermilov.books.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

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
    void saveShouldUpdate() {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        val saveAuthor = dao.save(new Author(id, "Author 2"));
        assertThat(dao.getByID(id).get().getName()).isEqualTo("Author 2");
    }

    @Test
    void saveNotShouldUpdateExistName() {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        dao.save(new Author(0L, "Author 2"));
        assertThrows(DuplicateKeyException.class, () -> {dao.save(new Author(id, "Author 2"));});
    }

    @Test
    void deleteShouldDelete() {
        val id = dao.save(new Author(0L, "Author 1")).getId();
        dao.deleteByID(id);
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}