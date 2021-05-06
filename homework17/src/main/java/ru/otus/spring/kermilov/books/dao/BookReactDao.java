package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.spring.kermilov.books.domain.Book;

public interface BookReactDao extends ReactiveMongoRepository<Book, String> {

}
