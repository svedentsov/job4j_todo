package ru.job4j.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryDBStore;

import java.util.Collection;
import java.util.Set;

/**
 * Верхний слой хранилища CategoryDbStore в котором находятся категории.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDBStore categoryStore;

    /**
     * Получить список всех категорий.
     *
     * @return список категорий
     */
    public Collection<Category> findAll() {
        return categoryStore.findAll();
    }

    /**
     * Получить список категорий по их идентификатору.
     *
     * @param id идентификаторы категорий
     * @return список категорий
     */
    public Set<Category> getCategories(Set<Integer> id) {
        return categoryStore.getCategories(id);
    }
}
