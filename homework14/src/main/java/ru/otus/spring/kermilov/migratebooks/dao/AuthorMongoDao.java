package ru.otus.spring.kermilov.migratebooks.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.kermilov.migratebooks.domain.AuthorMongo;

import java.util.Optional;

public interface AuthorMongoDao extends MongoRepository<AuthorMongo, String> {
    Optional<AuthorMongo> getByName(String name);
}
