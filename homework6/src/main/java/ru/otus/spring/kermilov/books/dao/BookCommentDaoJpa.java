package ru.otus.spring.kermilov.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Author;
import ru.otus.spring.kermilov.books.domain.Book;
import ru.otus.spring.kermilov.books.domain.BookComment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentDaoJpa implements BookCommentDao{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BookDao bookDao;

    @Override
    public BookComment save(BookComment a) {
        return em.merge(a);
    }

    @Override
    public List<BookComment> getByBookID(long book_id) {
        TypedQuery<BookComment> query = em.createQuery("select a " +
                        "from BookComment a " +
                        "where a.book.id = :book_id",
                BookComment.class);
        query.setParameter("book_id", book_id);
        return query.getResultList();
    }

    @Override
    public void deleteByBookID(long book_id) {
        Query query = em.createQuery("delete " +
                "from BookComment s " +
                "where s.book.id = :book_id");
        query.setParameter("book_id", book_id);
        query.executeUpdate();
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select a from BookComment a", BookComment.class);
        return query.getResultList();
    }
}
