package ru.otus.spring.kermilov.books.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookCommentDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;
import ru.otus.spring.kermilov.books.dto.BookDto;

@ExtendWith(MockitoExtension.class)
class BookDtoServiceTest {

    private static final long TEST_BOOK_1_ID = 1L;
    private static final String TEST_BOOK_1_NAME = "Test Book 1";
    private static final String TEST_AUTHOR_1_NAME = "Test Author 1";
    private static final String TEST_GENRE_1_NAME = "Test Genre 1";
    private static final String TEST_GENRE_2_NAME = "Test Genre 2";
    private static final List<String> TEST_GENRES_1_2_NAMES = List.of(TEST_GENRE_1_NAME, TEST_GENRE_2_NAME);

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
                    .id(TEST_BOOK_1_ID)
                    .name(TEST_BOOK_1_NAME)
                    .author(TEST_AUTHOR_1)
                    .genres(List.of(TEST_GENRE_1, TEST_GENRE_2))
                    .build();

    @Mock
    private BookDao bookDao;
    @Mock
    private BookCommentDao bookCommentDao;
    @Mock
    private AuthorDao authorDao;
    @Mock
    private GenreDao genreDao;

    private BookDtoService bookDtoService;

    @Test
    void findByIdTest() {
        bookDtoService = new BookDtoService(bookDao, bookCommentDao, authorDao, genreDao);

        when(bookDao.findById(TEST_BOOK_1_ID)).thenReturn(Optional.of(TEST_BOOK_1));

        BookDto bookDto = bookDtoService.findById(TEST_BOOK_1_ID);
        assertThat(bookDto).matches(b -> b.getId() == TEST_BOOK_1.getId(), "id matches")
                           .matches(b -> b.getName().equals(TEST_BOOK_1.getName()), "name matches")
                           .matches(b -> b.getAuthor().equals(TEST_BOOK_1.getAuthor().getName()), "author matches")
                           .matches(b -> b.getGenres().equals(TEST_BOOK_1.getGenres().stream().map(Genre::getName).collect(Collectors.joining(","))), "genres matches");
    }

    @Test
    void saveTest() {
        bookDtoService = new BookDtoService(bookDao, bookCommentDao, authorDao, genreDao);

        when(authorDao.getByName(TEST_AUTHOR_1_NAME)).thenReturn(Optional.of(TEST_AUTHOR_1));
        when(genreDao.getByName(TEST_GENRE_1_NAME)).thenReturn(Optional.of(TEST_GENRE_1));
        when(genreDao.getByName(TEST_GENRE_2_NAME)).thenReturn(Optional.of(TEST_GENRE_2));

        BookDto bookDto = BookDto.builder()
                .id(TEST_BOOK_1_ID)
                .name(TEST_BOOK_1_NAME)
                .author(TEST_AUTHOR_1_NAME)
                .genres(String.join(", ", TEST_GENRES_1_2_NAMES))
                .genresList(TEST_GENRES_1_2_NAMES)
                .build();
        bookDtoService.save(bookDto);
        verify(bookDao).save(TEST_BOOK_1);
    }

}