package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
        given(authorDao.getByID(1L)).willReturn(Optional.of(new Author(1L, "Author 1")));
        given(genreDao.getByID(1L)).willReturn(Optional.of(new Genre(1L, "Genre 1")));
    }

    @Test
    void saveTestCorrect() throws Exception {
        val saveBook = dao.save(new Book(0L, "Book 1", 1L, 1L));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 1" &&
                                     Book.getAuthorID() == 1L && Book.getGenreID() == 1L);
    }

    @Test
    void saveAndGetTestCorrect() throws Exception {
        val saveBook = dao.save(new Book(0L, "Book 1", 1L, 1L));
        assertThat(dao.getByID(saveBook.getId()).get()).usingRecursiveComparison().isEqualTo(saveBook);
    }

    @Test
    void saveAndGetTestIncorrect() throws Exception {
        given(authorDao.getByID(2L)).willReturn(Optional.of(new Author(2L, "Author 2")));
        given(genreDao.getByID(2L)).willReturn(Optional.of(new Genre(2L, "Genre 2")));
        val saveBook1 = dao.save(new Book(0L, "Book 1", 1L, 1L));
        val saveBook2 = dao.save(new Book(saveBook1.getId(), "Book 1", 2L, 2L));
        assertThat(saveBook2).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 1" &&
                Book.getAuthorID() == 2L && Book.getGenreID() == 2L);
    }

    @Test
    void saveAndGetTestNoAuthorException() throws Exception {
        Book a = new Book(1L, "Book 1", 2L, 1L);
        assertThrows(Exception.class, () -> dao.save(a));
    }

    @Test
    void saveAndGetTestNoGenreException() throws Exception {
        Book a = new Book(1L, "Book 1", 1L, 2L);
        assertThrows(Exception.class, () -> dao.save(a));
    }

    @Test
    void deleteTest() throws Exception {
        Book saveBook = new Book(0L, "Book 1", 1L, 1L);
        dao.save(saveBook);
        dao.deleteByID(saveBook.getId());
        assertThat(dao.getByID(saveBook.getId()).isEmpty()).isEqualTo(true);
    }
}