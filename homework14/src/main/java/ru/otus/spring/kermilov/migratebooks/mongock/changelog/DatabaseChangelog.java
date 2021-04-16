package ru.otus.spring.kermilov.migratebooks.mongock.changelog;

import java.util.List;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

import ru.otus.spring.kermilov.migratebooks.dao.AuthorMongoDao;
import ru.otus.spring.kermilov.migratebooks.dao.BookMongoDao;
import ru.otus.spring.kermilov.migratebooks.dao.GenreMongoDao;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorMongo;
import ru.otus.spring.kermilov.migratebooks.domain.BookMongo;
import ru.otus.spring.kermilov.migratebooks.domain.GenreMongo;

@ChangeLog
public class DatabaseChangelog {

    private static final AuthorMongo AUTHOR_1 = new AuthorMongo("f339278a-4010-48ce-92c2-3a113a181a02", "Author 1");
    private static final AuthorMongo AUTHOR_2 = new AuthorMongo("ccd8a5c3-7ff9-482f-89d5-ab613487ff93", "Author 2");
    private static final AuthorMongo AUTHOR_3 = new AuthorMongo("be8b998a-836c-4680-b76c-ac7b1ca57310", "Author 3");
    private static final GenreMongo GENRE_1 = new GenreMongo("394fdcea-c7dc-442c-aeba-a396c2fdb774", "Genre 1");
    private static final GenreMongo GENRE_2 = new GenreMongo("0d333aa5-f917-4353-85f2-d7e151c37e65", "Genre 2");
    private static final GenreMongo GENRE_3 = new GenreMongo("c9bedc1d-87f7-409a-8c08-20e3333ad2b4", "Genre 3");
    private static final BookMongo BOOK_1 = new BookMongo("e618dd51-4c8d-43e3-b4ac-ad01f6e2e917", "Book 1", AUTHOR_1, List.of(GENRE_1, GENRE_2));
    private static final BookMongo BOOK_2 = new BookMongo("51423a72-0c0c-404f-852b-0941c920242c", "Book 2", AUTHOR_2, List.of(GENRE_2));
    private static final BookMongo BOOK_3 = new BookMongo("1931cbf5-3b47-4f00-9d50-3f2a39b50bfd", "Book 3", AUTHOR_3, List.of(GENRE_3));
    @ChangeSet(order = "001", id = "dropDb", author = "kermilov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "003", id = "insertAuthors", author = "kermilov")
    public void insertAuthors(AuthorMongoDao repository) {
        repository.save(AUTHOR_1);
        repository.save(AUTHOR_2);
        repository.save(AUTHOR_3);
    }

    @ChangeSet(order = "004", id = "insertGenres", author = "kermilov")
    public void insertGenres(GenreMongoDao repository) {
        repository.save(GENRE_1);
        repository.save(GENRE_2);
        repository.save(GENRE_3);
    }

    @ChangeSet(order = "005", id = "insertBooks", author = "kermilov")
    public void insertBooks(BookMongoDao repository) {
        repository.save(BOOK_1);
        repository.save(BOOK_2);
        repository.save(BOOK_3);
    }

}
