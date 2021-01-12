package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;
    @MockBean
    private AuthorDao authorDao;
    @MockBean
    private GenreDao genreDao;

    @BeforeEach
    void beforeEach() {
        given(authorDao.getByID(1L)).willReturn(Optional.of(getAuthor(1L)));
        given(genreDao.getByID(1L)).willReturn(Optional.of(getGenre(1L)));
        given(authorDao.getByID(2L)).willReturn(Optional.of(getAuthor(2L)));
        given(genreDao.getByID(2L)).willReturn(Optional.of(getGenre(2L)));
    }

    private Author getAuthor(long num) {
        return new Author(num, "Test Author " + num);
    }

    private Genre getGenre(long num) {
        return new Genre(num, "Test Genre " + num);
    }

    @Test
    void saveShouldInsert() {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L)));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName().equals("Book 1") &&
                                     Book.getAuthor().getId() == 1L && Book.getGenre().getId() == 1L);
    }

    @Test
    void getShouldGet() {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L)));
        assertThat(dao.getByID(saveBook.getId()).get()).usingRecursiveComparison().isEqualTo(saveBook);
    }

    @Test
    void saveShouldUpdate() {
        val id = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L))).getId();
        val saveBook = dao.save(new Book(id, "Book 2", getAuthor(2L), getGenre(2L)));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 2" &&
                Book.getAuthor().getId() == 2L && Book.getGenre().getId() == 2L);
    }

    @Test
    void saveNotShouldUpdateExistName() {
        val id = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L))).getId();
        dao.save(new Book(0L, "Book 2", getAuthor(2L), getGenre(2L)));
        assertThrows(DuplicateKeyException.class, () -> {dao.save(new Book(id, "Book 2", getAuthor(2L), getGenre(2L)));});
    }

    @Test
    void saveShouldThrowsNoAuthorException() {
        Book a = new Book(1L, "Book 1", getAuthor(3L), getGenre(1L));
        assertThrows(DataIntegrityViolationException.class, () -> dao.save(a));
    }

    @Test
    void saveShouldThrowsNoGenreException() {
        Book a = new Book(1L, "Book 1", getAuthor(1L), getGenre(3L));
        assertThrows(DataIntegrityViolationException.class, () -> dao.save(a));
    }

    @Test
    void deleteShouldDelete() {
        Book saveBook = new Book(0L, "Book 1", getAuthor(1L), getGenre(1L));
        val id = dao.save(saveBook).getId();
        dao.deleteByID(id);
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }
}