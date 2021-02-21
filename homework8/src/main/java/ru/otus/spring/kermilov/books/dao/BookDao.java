package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.Book;

import java.util.Optional;

public interface BookDao extends MongoRepository<Book, String> {
    Optional<Book> getByName(String name);
}
