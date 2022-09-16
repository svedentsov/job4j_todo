package ru.job4j.todo.store;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
public class UserDBStore implements DBStore {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Создать аккаунт пользователя.
     *
     * @param user создаваемый аккаунт пользователя
     * @return объект user в виде контейнера optional
     */
    public Optional<User> add(User user) {
        return Optional.of((User) tx(session -> session.merge(user), sessionFactory));
    }

    /**
     * Осуществить поиск по двум критериям.
     *
     * @param email    электронная почта для входа
     * @param password пароль для входа
     * @return контейнер optional с объектом User, который прошел авторизацию
     */
    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return tx(session -> session
                        .createQuery("FROM User u WHERE u.email = :email AND u.password = :password")
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .uniqueResultOptional(),
                sessionFactory);
    }
}
