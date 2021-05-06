package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.books.domain.Book;

public interface BookDao extends MongoRepository<Book, String> {

}
