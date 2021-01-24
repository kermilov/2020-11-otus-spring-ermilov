package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.Book;

import java.util.Optional;

public interface BookDao extends CrudRepository<Book, Long> {
    Optional<Book> getByName(String name);
}
