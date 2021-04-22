package ru.otus.spring.kermilov.books.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.kermilov.books.domain.BookComment;

import java.util.List;

public interface BookCommentDao extends CrudRepository<BookComment, Long> {
    List<BookComment> getByBookId(long bookId);
    void deleteByBookId(long bookId);
}
