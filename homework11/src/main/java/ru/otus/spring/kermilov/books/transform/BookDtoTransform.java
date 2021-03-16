package ru.otus.spring.kermilov.books.transform;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;
import ru.otus.spring.kermilov.books.dto.BookDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookDtoTransform {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    private Genre saveGenre(String genreName) {
        Optional<Genre> byName = genreDao.getByName(genreName);
        if (byName.isPresent()) {
            return byName.get();
        }

        return genreDao.save(new Genre(genreName));
    }

    private Author saveAuthor(String authorName) {
        Optional<Author> byName = authorDao.getByName(authorName);
        if (byName.isPresent()) {
            return byName.get();
        }
        return authorDao.save(new Author(authorName));
    }

    public Book getBook(BookDto bookDto) {
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

    public static BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor().getName());
        bookDto.setGenres(book.getGenres().stream().map(g -> String.valueOf(g.getName())).collect(Collectors.joining(",")));
        return bookDto;
    }

}
