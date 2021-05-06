package ru.otus.spring.kermilov.books.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Genres") // Задает имя таблицы, на которую будет отображаться сущность
public class Genre {
    @Id
    private String id;
    @Field
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
