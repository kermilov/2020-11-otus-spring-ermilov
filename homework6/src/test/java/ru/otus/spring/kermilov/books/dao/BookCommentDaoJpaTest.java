package ru.otus.spring.kermilov.books.dao;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookCommentDaoJpa.class, BookDaoJpa.class})
class BookCommentDaoJpaTest {

    @Autowired
    private BookCommentDaoJpa dao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private TestEntityManager em;

    @Test
    void saveShouldInsert() {
        val saveBook = bookDao.save(new Book(0L, "Test Book 1", null, null));
        val saveBookComment = dao.save(new BookComment(0L, saveBook, "BookComment 1"));
        assertThat(saveBookComment).matches(BookComment -> BookComment.getBook() == saveBook && BookComment.getComment().equals("BookComment 1"));
    }
}