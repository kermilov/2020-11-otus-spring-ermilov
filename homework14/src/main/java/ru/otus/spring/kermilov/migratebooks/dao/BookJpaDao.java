package ru.otus.spring.kermilov.migratebooks.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.migratebooks.domain.BookJpa;

import java.util.List;
import java.util.Optional;

public interface BookJpaDao extends CrudRepository<BookJpa, Long> {
    List<BookJpa> findAll();
    Optional<BookJpa> getByName(String name);
}
