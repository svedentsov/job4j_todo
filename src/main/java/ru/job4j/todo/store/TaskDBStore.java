package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;

@Repository
public class TaskDBStore implements DBStore {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Создать задача.
     */
    public Task add(Task task) {
        return (Task) tx(session -> session.merge(task), sessionFactory);
    }

    /**
     * Удалить задачу.
     *
     * @param id идентификатор задачи
     */
    public void delete(int id) {
        tx(session -> session
                        .createQuery("DELETE Task t WHERE t.id = :id")
                        .setParameter("id", id)
                        .executeUpdate(),
                sessionFactory);
    }

    /**
     * Обновить задачу.
     *
     * @param task обновляемая задача
     */
    public void update(Task task) {
        tx(session -> {
                    session.update(task);
                    return task;
                }, sessionFactory
        );
    }

    /**
     * Получить список всех задач.
     */
    public List<Task> findAll() {
        return tx(session -> session
                .createQuery("SELECT DISTINCT t FROM Task t JOIN FETCH t.categories ORDER BY t.id")
                .getResultList(), sessionFactory);
    }

    /**
     * Получить задач по ID.
     *
     * @param id идентификатор задачи
     */
    public Task findById(int id) {
        return (Task) tx(session -> session
                .createQuery("SELECT DISTINCT t FROM Task t JOIN FETCH t.categories WHERE t.id = :id")
                .setParameter("id", id)
                .uniqueResult(), sessionFactory);
    }

    /**
     * Отметить задачу как выполненную.
     *
     * @param id идентификатор задачи
     */
    public void setDoneById(int id) {
        tx(session -> session
                        .createQuery("UPDATE Task t SET t.done = :flag WHERE t.id = :id")
                        .setParameter("flag", true)
                        .setParameter("id", id)
                        .executeUpdate(),
                sessionFactory);
    }

    /**
     * Отметить задачу как активную.
     *
     * @param id идентификатор задачи
     */
    public void setActiveById(int id) {
        tx(session -> session.createQuery("UPDATE Task t SET t.done = :flag WHERE t.id = :id")
                        .setParameter("flag", false)
                        .setParameter("id", id)
                        .executeUpdate(),
                sessionFactory);
    }

    /**
     * Получить список активных задач.
     */
    public List<Task> findActive() {
        return tx(session -> session
                        .createQuery("SELECT DISTINCT t FROM Task t JOIN FETCH t.categories WHERE t.done = :flag ORDER BY t.id")
                        .setParameter("flag", false)
                        .getResultList(),
                sessionFactory);
    }

    /**
     * Получить список завершенных задач.
     */
    public List<Task> findDone() {
        return tx(session -> session
                        .createQuery("SELECT DISTINCT t FROM Task t JOIN FETCH t.categories WHERE t.done = :flag ORDER BY t.id")
                        .setParameter("flag", true)
                        .getResultList(),
                sessionFactory);
    }
}
