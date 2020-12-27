package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @ShellMethod(value = "Save book.", key = {"s", "save"})
    public void saveBook(@ShellOption(help = "bookName") String bookName,
                         @ShellOption(help = "authorName") String authorName,
                         @ShellOption(help = "genreName") String genreName) {
        try {
            bookDao.save(new Book(0L, bookName,
                    authorDao.save(new Author(0L, authorName)).getId(),
                    genreDao.save(new Genre(0L, genreName)).getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ShellMethod(value = "List all books.", key = {"l", "list"})
    public void findAll() {
        bookDao.findAll().forEach(b -> System.out.println(b));
    }
}
