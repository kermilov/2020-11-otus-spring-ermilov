package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.User;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long> {
    Optional<User> getByName(String name);

}
