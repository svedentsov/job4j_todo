package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskDBStore;

import java.util.List;

/**
 * Верхний слой хранилища TaskDBStore в котором находятся задания.
 */
@ThreadSafe
@Service
public class TaskService {

    @Autowired
    private TaskDBStore tasksStore;

    /**
     * Добавить задачу.
     *
     * @param task добавляемая задача
     * @return возвращает task
     */
    public Task add(Task task) {
        return tasksStore.add(task);
    }

    /**
     * Удалить задачу по ID.
     *
     * @param id идентификатор задачи
     */
    public void delete(int id) {
        tasksStore.delete(id);
    }

    /**
     * Получить список всех задач.
     *
     * @return все задачи
     */
    public List<Task> findAll() {
        return tasksStore.findAll();
    }

    /**
     * Получить задачу по ID.
     *
     * @param id идентификатор заявки
     * @return возвращает task
     */
    public Task findById(int id) {
        return tasksStore.findById(id);
    }

    /**
     * Получить список активных задач.
     *
     * @return выполненные задачи
     */
    public List<Task> findActive() {
        return tasksStore.findActive();
    }

    /**
     * Получить список выполненных задач.
     *
     * @return выполненные задачи
     */
    public List<Task> findDone() {
        return tasksStore.findDone();
    }

    /**
     * Обновить задачу.
     *
     * @param task обновляемая задача
     */
    public void update(Task task) {
        tasksStore.update(task);
    }

    /**
     * Отметить задачу как выполненную.
     *
     * @param id идентификатор задачи
     */
    public void setDoneById(int id) {
        tasksStore.setDoneById(id);
    }

    /**
     * Отметить задачу как активную.
     *
     * @param id идентификатор задачи
     */
    public void setActiveById(int id) {
        tasksStore.setActiveById(id);
    }
}
