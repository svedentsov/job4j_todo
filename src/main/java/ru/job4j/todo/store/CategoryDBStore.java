package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CategoryDBStore implements DBStore {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Category> findAll() {
        return tx(session -> session
                        .createQuery("FROM Category ORDER BY id")
                        .getResultList(),
                sessionFactory);
    }

    private Category findById(int id) {
        return (Category) tx(session -> session
                .createQuery("FROM Category WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult(), sessionFactory);
    }

    public Set<Category> getCategories(Set<Integer> categoryID) {
        Set<Category> categories = new HashSet<>();
        for (Integer id : categoryID) {
            categories.add(findById(id));
        }
        return categories;
    }
}
