package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.books.domain.Author;

import java.util.Optional;

public interface AuthorDao extends MongoRepository<Author, String> {
    Optional<Author> getByName(String name);
}
