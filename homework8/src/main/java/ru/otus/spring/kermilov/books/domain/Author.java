package ru.otus.spring.kermilov.books.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Authors") // Задает имя таблицы, на которую будет отображаться сущность
public class Author {
    @Id
    private String id;
    @Field
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
