package ru.otus.spring.kermilov.migratebooks.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ru.otus.spring.kermilov.migratebooks.domain.GenreJpa;

public interface GenreJpaDao extends CrudRepository<GenreJpa, Long> {
    Optional<GenreJpa> getByName(String name);
    List<GenreJpa> getByNameIn(List<String> names);

}
