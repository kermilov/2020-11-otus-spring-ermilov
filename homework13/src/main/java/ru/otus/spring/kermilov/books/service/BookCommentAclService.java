package ru.otus.spring.kermilov.books.service;

import ru.otus.spring.kermilov.books.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentAclService {
    List<BookComment> getByBookId(long id);

    Optional<BookComment> findById(Long id);

    BookComment save(BookComment entity);

    void deleteById(long id);
}
