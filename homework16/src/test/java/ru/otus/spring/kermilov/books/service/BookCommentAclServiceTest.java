package ru.otus.spring.kermilov.books.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.dao.UserDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;
import ru.otus.spring.kermilov.books.domain.Genre;
import ru.otus.spring.kermilov.books.domain.User;

@SpringBootTest
class BookCommentAclServiceTest {

    private static final String TEST_BOOK_1_NAME = "Test Book Save 1";
    private static final String TEST_AUTHOR_1_NAME = "Test Author 1";
    private static final String TEST_GENRE_1_NAME = "Test Genre 1";
    private static final String TEST_GENRE_2_NAME = "Test Genre 2";

    private static final Author TEST_AUTHOR_1 = Author.builder()
                    .id(1L)
                    .name(TEST_AUTHOR_1_NAME)
                    .build();
    private static final Genre TEST_GENRE_1 = Genre.builder()
                    .id(1L)
                    .name(TEST_GENRE_1_NAME)
                    .build();
    private static final Genre TEST_GENRE_2 = Genre.builder()
                    .id(2L)
                    .name(TEST_GENRE_2_NAME)
                    .build();
    private static final Book TEST_BOOK_1 = Book.builder()
                    .name(TEST_BOOK_1_NAME)
                    .author(TEST_AUTHOR_1)
                    .genres(List.of(TEST_GENRE_1, TEST_GENRE_2))
                    .build();

    @Autowired
    private BookCommentAclService bookCommentAclService;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;

    @Test
    @WithMockUser(username = "OtusTeacher")
    void getByBookIdTest() {
        var result = bookCommentAclService.getByBookId(1L);
        assertThat(result).matches(r -> r.size() == 1, "size matches")
                          .matches(r -> r.get(0).getComment().equals("Test Book 1 Comment 1"), "comment matches");
    }
    @Test
    @WithMockUser(username = "Kermilov")
    void notGetByBookIdTest() {
        var result = bookCommentAclService.getByBookId(1L);
        assertThat(result).matches(r -> r.size() == 1, "size matches")
                          .matches(r -> r.get(0).getComment().equals("Test Book 1 Comment 2"), "comment matches");
    }

    @Test
    @WithMockUser(username = "Kermilov")
    void findByIdTest() {
        var result = bookCommentAclService.findById(2L);
        assertTrue(result.isPresent());
    }
    @Test
    @WithMockUser(username = "OtusTeacher")
    void notFindByIdTest() {
        var result = bookCommentAclService.findById(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    @WithMockUser(username = "OtusTeacher")
    void saveTest() {
        var book = bookDao.save(TEST_BOOK_1);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.getByName(authentication.getName()).get();
        BookComment bookComment = BookComment.builder()
                    .book(book)
                    .user(user)
                    .comment("comment")
                    .build();
        var save = bookCommentAclService.save(bookComment);
        var find = bookCommentAclService.findById(save.getId()).get();
        assertThat(save).usingRecursiveComparison().isEqualTo(find);
    }

    @Test
    @WithMockUser(username = "OtusTeacher")
    void deleteByIdTest() {
        bookCommentAclService.deleteById(1L);
        assertTrue(bookCommentAclService.findById(1L).isEmpty());
    }
    @Test
    @WithMockUser(username = "Kermilov")
    void notDeleteByIdTest() {
        assertThrows(RuntimeException.class, () -> bookCommentAclService.deleteById(1L));
    }

}