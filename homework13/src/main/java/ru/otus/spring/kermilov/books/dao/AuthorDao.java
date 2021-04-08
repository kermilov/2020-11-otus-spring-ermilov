package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.Author;

import java.util.Optional;

public interface AuthorDao extends CrudRepository<Author, Long> {
    Optional<Author> getByName(String name);
}
