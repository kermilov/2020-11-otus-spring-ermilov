package ru.otus.spring.kermilov.migratebooks.service;

import org.springframework.stereotype.Service;

import ru.otus.spring.kermilov.migratebooks.domain.AuthorJpa;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorMongo;

@Service
public class AuthorMigrate {

    public AuthorJpa migrate(AuthorMongo authorMongo) {
        return AuthorJpa.builder()
            .name(authorMongo.getName())
            .build();
    }
}
