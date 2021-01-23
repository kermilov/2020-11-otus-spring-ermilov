package ru.otus.spring.kermilov.books.dao;

import ru.otus.spring.kermilov.books.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre save(Genre a);
    Optional<Genre> getByID(long id);
    Optional<Genre> getByName(String name);
    void remove(Genre a);
    List<Genre> findAll();
}
