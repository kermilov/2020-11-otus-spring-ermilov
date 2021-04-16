package ru.otus.spring.kermilov.migratebooks.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorJpa;

import java.util.Optional;

public interface AuthorJpaDao extends CrudRepository<AuthorJpa, Long> {
    Optional<AuthorJpa> getByName(String name);
}
