package ru.otus.spring.kermilov.books.dao;

import ru.otus.spring.kermilov.books.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author save(Author a);
    Optional<Author> getByID(long id);
    Optional<Author> getByName(String name);
    void remove(Author a);
    List<Author> findAll();
}
