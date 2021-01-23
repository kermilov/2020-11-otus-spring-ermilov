package ru.otus.spring.kermilov.books.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.BookComment;

import javax.persistence.*;

@Repository
public class BookCommentDaoJpa implements BookCommentDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public BookComment save(BookComment a) {
        return em.merge(a);
    }
}
