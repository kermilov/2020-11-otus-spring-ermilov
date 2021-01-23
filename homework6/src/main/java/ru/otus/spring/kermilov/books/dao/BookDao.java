package ru.otus.spring.kermilov.books.dao;

import ru.otus.spring.kermilov.books.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book save(Book a);
    Optional<Book> getByID(long id);
    Optional<Book> getByName(String name);
    void remove(Book a);
    List<Book> findAll();
}
