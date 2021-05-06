package ru.otus.spring.kermilov.books.mongock.changelog;

import java.util.List;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

import ru.otus.spring.kermilov.books.dao.AuthorDao;
import ru.otus.spring.kermilov.books.dao.BookDao;
import ru.otus.spring.kermilov.books.dao.GenreDao;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    private static final Author AUTHOR_1 = new Author("f339278a-4010-48ce-92c2-3a113a181a02", "Author 1");
    private static final Author AUTHOR_2 = new Author("ccd8a5c3-7ff9-482f-89d5-ab613487ff93", "Author 2");
    private static final Author AUTHOR_3 = new Author("be8b998a-836c-4680-b76c-ac7b1ca57310", "Author 3");
    private static final Genre GENRE_1 = new Genre("394fdcea-c7dc-442c-aeba-a396c2fdb774", "Genre 1");
    private static final Genre GENRE_2 = new Genre("0d333aa5-f917-4353-85f2-d7e151c37e65", "Genre 2");
    private static final Genre GENRE_3 = new Genre("c9bedc1d-87f7-409a-8c08-20e3333ad2b4", "Genre 3");
    private static final Book BOOK_1 = new Book("e618dd51-4c8d-43e3-b4ac-ad01f6e2e917", "Book 1", AUTHOR_1, List.of(GENRE_1, GENRE_2));
    private static final Book BOOK_2 = new Book("51423a72-0c0c-404f-852b-0941c920242c", "Book 2", AUTHOR_2, List.of(GENRE_2));
    private static final Book BOOK_3 = new Book("1931cbf5-3b47-4f00-9d50-3f2a39b50bfd", "Book 3", AUTHOR_3, List.of(GENRE_3));
    @ChangeSet(order = "001", id = "dropDb", author = "kermilov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "kermilov")
    public void insertAuthors(AuthorDao repository) {
        repository.save(AUTHOR_1);
        repository.save(AUTHOR_2);
        repository.save(AUTHOR_3);
    }

    @ChangeSet(order = "004", id = "insertGenres", author = "kermilov")
    public void insertGenres(GenreDao repository) {
        repository.save(GENRE_1);
        repository.save(GENRE_2);
        repository.save(GENRE_3);
    }

    @ChangeSet(order = "005", id = "insertBooks", author = "kermilov")
    public void insertBooks(BookDao repository) {
        repository.save(BOOK_1);
        repository.save(BOOK_2);
        repository.save(BOOK_3);
    }

}
