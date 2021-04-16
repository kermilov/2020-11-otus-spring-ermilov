package ru.otus.spring.kermilov.migratebooks.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.kermilov.migratebooks.domain.GenreJpa;
import ru.otus.spring.kermilov.migratebooks.domain.GenreMongo;

@Service
public class GenreMigrate {

    public GenreJpa migrate(GenreMongo genreMongo) {
        return GenreJpa.builder()
            .name(genreMongo.getName())
            .build();
    }
}
