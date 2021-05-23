package ru.otus.spring.kermilov.books.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Genres") // Задает имя таблицы, на которую будет отображаться сущность
public class Genre {
    @Id // Позволяет указать какое поле является идентификатором
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Стратегия генерации идентификаторов
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
