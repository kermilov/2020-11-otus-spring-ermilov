package ru.otus.spring.kermilov.books.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Authors") // Задает имя таблицы, на которую будет отображаться сущность
public class Author {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
