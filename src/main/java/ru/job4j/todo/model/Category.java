package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Класс характеризует категорию.
 */
@Data
@Entity
@Table(name = "categories")
public class Category {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Наименование.
     */
    private String name;
}
