package ru.otus.spring.kermilov.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookCommentDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;
import ru.otus.spring.kermilov.books.domain.Genre;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final BookDao bookDao;
    private final BookCommentDao bookCommentDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Transactional
    @ShellMethod(value = "Save genre.", key = {"sg", "saveGenre"})
    public Genre saveGenre(@ShellOption(help = "genreName") String genreName) {
        Optional<Genre> byName = genreDao.getByName(genreName);
        if (!byName.isEmpty()) {
            return byName.get();
        }
        return genreDao.save(new Genre(0L, genreName));
    }

    @Transactional
    @ShellMethod(value = "Save author.", key = {"sa", "saveAuthor"})
    public Author saveAuthor(@ShellOption(help = "authorName") String authorName) {
        Optional<Author> byName = authorDao.getByName(authorName);
        if (!byName.isEmpty()) {
            return byName.get();
        }
        return authorDao.save(new Author(0L, authorName));
    }

    @Transactional
    @ShellMethod(value = "Save book.", key = {"s", "save"})
    public Book saveBook(@ShellOption(help = "bookName") String bookName,
                         @ShellOption(help = "authorName") String authorName,
                         @ShellOption(help = "genreNames") String genreNames) {
        List<Genre> genres = new ArrayList<Genre>();
        for (String genreName : genreNames.split(",")) {
            genres.add(saveGenre(genreName));
        }
        Author author = saveAuthor(authorName);
        Optional<Book> byName = bookDao.getByName(bookName);
        Book b;
        if (byName.isPresent()) {
            b = byName.get();
            b.setAuthor(author);
            b.setGenres(genres);
        }
        else {
            b = new Book(0L, bookName, author, genres);
        }
        return bookDao.save(b);
    }

    @Transactional(readOnly=true)
    @ShellMethod(value = "List all books.", key = {"l", "list"})
    public void findAll() {
        bookDao.findAll().forEach(b -> System.out.println(b));
    }

    @Transactional
    @ShellMethod(value = "Delete book.", key = {"d", "delete"})
    public void deleteBook(@ShellOption(help = "bookName") String bookName) {
        try {
            Optional<Book> optionalBook = bookDao.getByName(bookName);
            if (!optionalBook.isEmpty()) {
                bookDao.delete(optionalBook.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly=true)
    @ShellMethod(value = "Find book.", key = {"f", "find"})
    public void findBook(@ShellOption(help = "bookName") String bookName) {
        try {
            Optional<Book> optionalBook = bookDao.getByName(bookName);
            if (!optionalBook.isEmpty()) {
                System.out.println(optionalBook.get());
            }
            else {
                System.out.println("No such book");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @ShellMethod(value = "Save book comment.", key = {"sc", "saveComment"})
    public void saveBookComment(@ShellOption(help = "bookName") String bookName,
                         @ShellOption(help = "bookComment") String bookComment) {
        bookCommentDao.save(
                new BookComment(0L, bookDao.getByName(bookName).get(), bookComment)
        );
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book comments.", key = {"fc", "findComments"})
    public void findBookComment(@ShellOption(help = "bookName") String bookName) {
        System.out.println(bookCommentDao.getByBookId(bookDao.getByName(bookName).get().getId()));
    }

    @Transactional
    @ShellMethod(value = "Delete book comments.", key = {"dc", "deleteComments"})
    public void deleteBookComments(@ShellOption(help = "bookName") String bookName) {
        bookCommentDao.deleteByBookId(bookDao.getByName(bookName).get().getId());
    }

}
