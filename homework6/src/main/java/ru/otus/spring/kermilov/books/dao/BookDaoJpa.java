package ru.otus.spring.kermilov.books.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class BookDaoJpa implements BookDao
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book a) {
        if (a.getId() > 0) {
            return em.merge(a);
        }
        else {
            em.persist(a);
            return a;
        }
    }

    @Override
    public Optional<Book> getByID(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> getByName(String name) {
        TypedQuery<Book> query = em.createQuery("select a " +
                        "from Book a " +
                        "where a.name = :name",
                Book.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByID(long id) {
        Query query = em.createQuery("delete " +
                "from Book s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select a from Book a", Book.class);
        query.setHint("javax.persistence.fetchgraph", em.getEntityGraph("books-author-entity-graph"));
        return query.getResultList();
    }
}
