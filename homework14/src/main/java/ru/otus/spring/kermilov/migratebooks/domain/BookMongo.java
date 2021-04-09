package ru.otus.spring.kermilov.migratebooks.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Books") // Задает имя таблицы, на которую будет отображаться сущность
public class BookMongo {
    @Id
    private String id;
    @Field
    private String name;
    @DBRef
    private AuthorMongo author;
    @DBRef
    private List<GenreMongo> genres;

    public BookMongo(String name, AuthorMongo author, List<GenreMongo> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public BookMongo(String id) {
        this.id = id;
    }
}
