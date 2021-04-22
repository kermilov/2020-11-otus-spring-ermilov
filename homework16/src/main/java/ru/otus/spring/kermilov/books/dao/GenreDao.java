package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.Genre;

import java.util.Optional;

public interface GenreDao extends CrudRepository<Genre, Long> {
    Optional<Genre> getByName(String name);

}
