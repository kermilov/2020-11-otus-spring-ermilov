package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookCommentDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
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
    private final BookDao bookDao;
    private final BookCommentDao bookCommentDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Transactional(readOnly=true)
    public List<BookDto> findAll() {
        return bookDao.findAll().stream().map(this::getBookDto).collect(Collectors.toList());
    }

    public BookDto getNew() {
        return new BookDto(0L,"","","");
    }

    @Transactional(readOnly=true)
    public BookDto findById(long id) {
        return getBookDto(bookDao.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public void save(BookDto bookDto) {
        bookDao.save(getBook(bookDto));
    }

    @Transactional
    public void deleteById(long id) {
        bookCommentDao.deleteByBookId(id);
        bookDao.deleteById(id);
    }

    private Genre saveGenre(String genreName) {
        Optional<Genre> byName = genreDao.getByName(genreName);
        if (!byName.isEmpty()) {
            return byName.get();
        }
        return genreDao.save(new Genre(0L, genreName));
    }

    private Author saveAuthor(String authorName) {
        Optional<Author> byName = authorDao.getByName(authorName);
        if (!byName.isEmpty()) {
            return byName.get();
        }
        return authorDao.save(new Author(0L, authorName));
    }

    private Book getBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setAuthor(saveAuthor(bookDto.getAuthor()));
        book.setGenres(bookDto.getGenresList().stream()
            .map(this::saveGenre)
            .collect(Collectors.toList()));
        return book;
    }

    private BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setGenres(book.getGenres().stream().map(Genre::getName).collect(Collectors.joining(",")));
        bookDto.setGenresList(book.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
        return bookDto;
    }

}
