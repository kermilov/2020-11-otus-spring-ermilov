package ru.otus.spring.kermilov.migratebooks.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Authors") // Задает имя таблицы, на которую будет отображаться сущность
public class AuthorMongo {
    @Id
    private String id;
    @Field
    private String name;

    public AuthorMongo(String name) {
        this.name = name;
    }
}
