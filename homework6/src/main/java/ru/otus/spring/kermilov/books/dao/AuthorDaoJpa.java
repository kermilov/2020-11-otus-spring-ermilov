package ru.otus.spring.kermilov.books.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.kermilov.books.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class AuthorDaoJpa implements AuthorDao
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author a) {
        Optional<Author> byName = getByName(a.getName());
        Optional<Author> byID = (a.getId() > 0) ? getByID(a.getId()) : Optional.empty();
        if (!byName.isEmpty()) {
            if (byID.isEmpty() || byID.get().equals(byName.get())) {
                return byName.get();
            } else {
                // merge молча ничего не делает, странный, сделаю за него
                throw new PersistenceException("Can't save cause here another Book with this name");
            }
        }
        if (!byID.isEmpty()) {
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
    public void deleteByID(long id) {
        Query query = em.createQuery("delete " +
                "from Author s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
