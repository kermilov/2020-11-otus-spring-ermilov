package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BookDaoJpa.class})
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa dao;
    @Autowired
    private TestEntityManager em;

    private Author getAuthor(long num) {
        return new Author(num, "Test Author " + num);
    }

    private Genre getGenre(long num) {
        return new Genre(num, "Test Genre " + num);
    }
    private List<Genre> getGenres(long num) {
        List<Genre> result = new ArrayList<>();
        result.add(getGenre(num));
        return result;
    }

    @Test
    void saveShouldInsert() {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenres(1L)));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName().equals("Book 1") &&
                                     Book.getAuthor().getId() == 1L && Book.getGenres().get(0).getId() == 1L);
    }

    @Test
    void getShouldGet() {
        val saveBook = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenres(1L)));
        assertThat(dao.getByID(saveBook.getId()).get()).usingRecursiveComparison().isEqualTo(saveBook);
    }

    @Test
    void saveShouldUpdateById() {
        val id = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenres(1L))).getId();
        val saveBook = dao.save(new Book(id, "Book 2", getAuthor(2L), getGenres(2L)));
        assertThat(saveBook).matches(Book -> Book.getId() > 0L && Book.getName() == "Book 2" &&
                Book.getAuthor().getId() == 2L && Book.getGenres().get(0).getId() == 2L);
    }

    @Test
    void saveShouldThrowsNoAuthorException() {
        Book a = new Book(1L, "Book 1", getAuthor(3L), getGenres(1L));
        assertThrows(PersistenceException.class, () -> dao.save(a));
    }

    @Test
    void saveShouldThrowsNoGenreException() {
        Book a = new Book(1L, "Book 1", getAuthor(1L), getGenres(3L));
        assertThrows(PersistenceException.class, () -> dao.save(a));
    }

    @Test
    void removeShouldDelete() {
        Book a = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenres(1L)));
        val id = a.getId();
        dao.remove(a);
        assertThat(dao.getByID(id).isEmpty()).isEqualTo(true);
    }

    @Test
    void findAllShouldNonNPlusOne() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);

        Book saveBook1 = dao.save(new Book(0L, "Book 1", getAuthor(1L), getGenres(1L)));
        Book saveBook2 = dao.save(new Book(0L, "Book 2", getAuthor(1L), getGenres(1L)));

        sessionFactory.getStatistics().setStatisticsEnabled(true);
        em.clear();
        val students = dao.findAll();
        System.out.println(students);
        assertThat(students).isNotNull().hasSize(2)
                .allMatch(s -> !s.getName().equals(""))
                .allMatch(s -> s.getGenres() != null)
                .allMatch(s -> s.getAuthor() != null)
                //.allMatch(s -> s.getEmails() != null && s.getEmails().size() > 0)
                ;
       //System.out.println("getPrepareStatementCount="+sessionFactory.getStatistics().getPrepareStatementCount());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(2);

    }
}