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
        given(authorDao.getByID(1L)).willReturn(Optional.of(getAuthor(1L)));
        given(genreDao.getByID(1L)).willReturn(Optional.of(getGenre(1L)));
    }

    private Author getAuthor(long num) {
        return new Author(num, "Author " + num);
    }

    private Genre getGenre(long num) {
        return new Genre(num, "Genre " + num);
    }

    @Test
    void saveTestCorrect() throws Exception {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L)));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 1" &&
                                     Book.getAuthor().getId() == 1L && Book.getGenre().getId() == 1L);
    }

    @Test
    void saveAndGetTestCorrect() throws Exception {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L)));
        assertThat(dao.getByID(saveBook.getId()).get()).usingRecursiveComparison().isEqualTo(saveBook);
    }

    @Test
    void saveAndGetTestIncorrect() throws Exception {
        given(authorDao.getByID(2L)).willReturn(Optional.of(getAuthor(2L)));
        given(genreDao.getByID(2L)).willReturn(Optional.of(getGenre(2L)));
        val saveBook1 = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenre(1L)));
        val saveBook2 = dao.save(new Book(saveBook1.getId(), "Book 1", getAuthor(2L), getGenre(2L)));
        assertThat(saveBook2).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 1" &&
                Book.getAuthor().getId() == 2L && Book.getGenre().getId() == 2L);
    }

    @Test
    void saveAndGetTestNoAuthorException() throws Exception {
        Book a = new Book(1L, "Book 1", getAuthor(2L), getGenre(1L));
        assertThrows(Exception.class, () -> dao.save(a));
    }

    @Test
    void saveAndGetTestNoGenreException() throws Exception {
        Book a = new Book(1L, "Book 1", getAuthor(1L), getGenre(2L));
        assertThrows(Exception.class, () -> dao.save(a));
    }

    @Test
    void deleteTest() throws Exception {
        Book saveBook = new Book(0L, "Book 1", getAuthor(1L), getGenre(1L));
        dao.save(saveBook);
        dao.deleteByID(saveBook.getId());
        assertThat(dao.getByID(saveBook.getId()).isEmpty()).isEqualTo(true);
    }
}