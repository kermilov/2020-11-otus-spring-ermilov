package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.books.domain.Genre;

import java.util.Optional;

public interface GenreDao extends MongoRepository<Genre, String> {
    Optional<Genre> getByName(String name);

}
