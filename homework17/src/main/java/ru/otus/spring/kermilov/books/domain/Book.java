package ru.otus.spring.kermilov.books.domain;

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
public class Book {
    @Id
    private String id;
    @Field
    private String name;
    @DBRef
    private Author author;
    @DBRef
    private List<Genre> genres;

    public Book(String name, Author author, List<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public Book(String id) {
        this.id = id;
    }
}
