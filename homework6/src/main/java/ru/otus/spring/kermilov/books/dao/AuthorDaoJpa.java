package ru.otus.spring.kermilov.books.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class AuthorDaoJpa implements AuthorDao
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author a) {
        if (a.getId() > 0) {
            return em.merge(a);
        }
        else {
            em.persist(a);
            return a;
        }
    }

    @Override
    public Optional<Author> getByID(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a " +
                        "from Author a " +
                        "where a.name = :name",
                Author.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        }
        catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Author a) {
        em.remove(a);
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
