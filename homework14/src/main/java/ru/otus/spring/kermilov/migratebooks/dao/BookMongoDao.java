package ru.otus.spring.kermilov.migratebooks.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.migratebooks.domain.BookMongo;

public interface BookMongoDao extends MongoRepository<BookMongo, String> {

}
