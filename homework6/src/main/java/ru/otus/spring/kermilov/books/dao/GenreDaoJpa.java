package ru.otus.spring.kermilov.books.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.otus.spring.kermilov.books.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class GenreDaoJpa implements GenreDao
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre a) {
        Optional<Genre> byName = getByName(a.getName());
        Optional<Genre> byID = (a.getId() > 0) ? getByID(a.getId()) : Optional.empty();
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
    public Optional<Genre> getByID(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select a " +
                        "from Genre a " +
                        "where a.name = :name",
                Genre.class);
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
                "from Genre s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select a from Genre a", Genre.class);
        return query.getResultList();
    }
}
