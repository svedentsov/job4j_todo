package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    /**
     * Сохранение нового аккаунта в БД
     *
     * @param model обрабатывает полученные данные
     * @param user  аккаунт для сохранения
     * @return ссылку на страницу авторизации
     */
    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Ошибка регистрации!");
            return "fail";
        }
        model.addAttribute("message", "Регистрация прошла успешно!");
        return "success";
    }

    /**
     * В случае если вход произошел неуспешно.
     *
     * @param model обрабатывает полученные данные
     * @param fail  ссылка на параметр в случае неудачной авторизации
     * @return ссылку на страницу авторизации
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Вход на сайт с помощью вызова логина и пароля.
     *
     * @param user текущий аккаунт
     * @param req  запрос на получение информации от сервера
     * @return в случае авторизации возвращает на страницу с заявками
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/allTasks";
    }

    /**
     * Если авторизация не произошла, то пользователя отправят на страницу авторизации или пользователь захотел выйти.
     *
     * @param session текущая сессия
     * @return ссылка на страницу авторизации
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
