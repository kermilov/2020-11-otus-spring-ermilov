package ru.otus.spring.kermilov.books.dao;

import ru.otus.spring.kermilov.books.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book a) throws Exception;
    Optional<Book> getByID(Long id);
    Optional<Book> getByName(String name);
    void deleteByID(Long id);
    List<Book> findAll();
}
