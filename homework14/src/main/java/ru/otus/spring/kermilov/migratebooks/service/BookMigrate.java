package ru.otus.spring.kermilov.migratebooks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.migratebooks.dao.AuthorJpaDao;
import ru.otus.spring.kermilov.migratebooks.dao.GenreJpaDao;
import ru.otus.spring.kermilov.migratebooks.domain.BookJpa;
import ru.otus.spring.kermilov.migratebooks.domain.BookMongo;
import ru.otus.spring.kermilov.migratebooks.domain.GenreMongo;

@Service
@RequiredArgsConstructor
public class BookMigrate {

    private final AuthorJpaDao authorJpaDao;
    private final GenreJpaDao genreJpaDao;

    public BookJpa migrate(BookMongo bookMongo) {
        List<String> names = bookMongo.getGenres().stream().map(GenreMongo::getName).collect(Collectors.toList());
        return BookJpa.builder()
            .name(bookMongo.getName())
            .author(authorJpaDao.getByName(bookMongo.getAuthor().getName()).get())
            .genres(genreJpaDao.getByNameIn(names))
            .build();
    }
}
