package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserDBStore;

import java.util.Optional;

/**
 * Верхний слой хранилища UserDbStore в котором находятся пользователи.
 */
@ThreadSafe
@Service
public class UserService {

    @Autowired
    private UserDBStore userStore;

    /**
     * Создать аккаунт пользователя.
     *
     * @param user создаваемый аккаунт пользователя
     * @return объект user в виде контейнера optional
     */
    public Optional<User> add(User user) {
        return userStore.add(user);
    }

    /**
     * Осуществить поиск по двум критериям.
     *
     * @param email    электронная почта для входа
     * @param password пароль для входа
     * @return контейнер optional с объектом User, который прошел авторизацию
     */
    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return userStore.findUserByEmailAndPwd(email, password);
    }
}
