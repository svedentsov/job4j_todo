package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс характеризует пользователя.
 */
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Имя.
     */
    private String name;
    /**
     * Email.
     */
    private String email;
    /**
     * Пароль.
     */
    private String password;
}
