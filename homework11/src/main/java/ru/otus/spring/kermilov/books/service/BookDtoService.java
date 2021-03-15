package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookCommentReactDao;
import ru.otus.spring.kermilov.books.dao.BookReactDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;
import ru.otus.spring.kermilov.books.dto.BookDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookDtoService {
    private final BookReactDao bookReactDao;
    private final BookCommentReactDao bookCommentReactDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Transactional(readOnly=true)
    public Flux<BookDto> findAll() {
        return bookReactDao.findAll().map(this::getBookDto);
    }

    @Transactional(readOnly=true)
    public Mono<BookDto> findById(String id) {
        return bookReactDao.findById(id).map(this::getBookDto);
    }

    @Transactional
    public Mono<BookDto> save(BookDto bookDto) {
        return bookReactDao.save(getBook(bookDto)).map(this::getBookDto);
    }

    @Transactional
    public Mono<Void> deleteById(String id) {
        return bookReactDao.deleteById(id);
    }

    private Genre saveGenre(String genreName) {
        Optional<Genre> byName = genreDao.getByName(genreName);
        if (!byName.isEmpty()) {
            return byName.get();
        }

        return genreDao.save(new Genre(genreName));
    }

    private Author saveAuthor(String authorName) {
        Optional<Author> byName = authorDao.getByName(authorName);
        if (!byName.isEmpty()) {
            return byName.get();
        }
        return authorDao.save(new Author(authorName));
    }

    private Book getBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setAuthor(saveAuthor(bookDto.getAuthor()));
        List<Genre> genres = Arrays.stream(bookDto.getGenres().split(","))
                .map(this::saveGenre)
                .collect(Collectors.toList());
        book.setGenres(genres);
        return book;
    }

    private BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setGenres(book.getGenres().stream().map(g -> String.valueOf(g.getName())).collect(Collectors.joining(",")));
        return bookDto;
    }

}
