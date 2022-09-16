package ru.job4j.todo.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс характеризует задачу.
 */
@Data
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Название.
     */
    private String name;
    /**
     * Описание.
     */
    private String description;
    /**
     * Дата и время создания.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private final Date created = new Date(System.currentTimeMillis());
    /**
     * Готовность.
     */
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Список категорий для заданной задачи.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();
}
