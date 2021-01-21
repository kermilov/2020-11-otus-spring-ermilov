package ru.otus.spring.kermilov.books.dao;

import ru.otus.spring.kermilov.books.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentDao {
    BookComment save(BookComment a);
    List<BookComment> getByBookID(long book_id);
    void deleteByBookID(long book_id);
    List<BookComment> findAll();
}
