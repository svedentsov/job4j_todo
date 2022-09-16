package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс-фильтр, осуществляет фильтрацию запросов.
 * Если запрос содержит данных о пользователе, то переадресует на страницу создания заданий.
 * Если запрос не содержит данных о пользователе, то переадресует на страницу авторизации либо регистрации.
 */
@Component
public class AuthFilter implements Filter {
    /**
     * Фильтр является связующим элементом для получения информации об аккаунтах пользователя.
     *
     * @param servletRequest  принимает запрос от пользователя
     * @param servletResponse сервер отправляет ответ на запрос
     * @param filterChain     обрабатывает входящую информацию от пользователя req/res(запрос/ответ)
     * @throws IOException может вызвать IOException в связи с неправильным запросом
     * @throws ServletException может вызвать ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage")
                || uri.endsWith("login")
                || uri.endsWith("registration")) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        filterChain.doFilter(req, res);
    }
}
