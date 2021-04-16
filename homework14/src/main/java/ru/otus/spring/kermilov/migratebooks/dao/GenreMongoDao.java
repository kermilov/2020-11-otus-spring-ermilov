package ru.otus.spring.kermilov.migratebooks.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.spring.kermilov.migratebooks.domain.GenreMongo;

public interface GenreMongoDao extends MongoRepository<GenreMongo, String> {
    Optional<GenreMongo> getByName(String name);
    List<GenreMongo> getByNameIn(List<String> names);

}
