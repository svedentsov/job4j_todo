package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.Set;

@ThreadSafe
@Controller
public class TaskController {

    private static final String TASKS = "tasks";

    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;

    private void setUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        model.addAttribute("user", user);
    }

    /**
     * Получить список всех задач.
     */
    @GetMapping("/allTasks")
    public String allTasks(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute(TASKS, taskService.findAll());
        return "allTasks";
    }

    /**
     * Получить задачу.
     */
    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute("categories", categoryService.findAll());
        return "addTask";
    }

    /**
     * Добавить задачу.
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam Set<Integer> categoryID,
                             HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        task.setCategories(categoryService.getCategories(categoryID));
        taskService.add(task);
        return "redirect:/allTasks";
    }

    /**
     * Получить задачу по ID.
     */
    @GetMapping("/description/{taskId}")
    public String description(Model model, @PathVariable("taskId") int id, HttpSession session) {
        setUser(model, session);
        model.addAttribute("task", taskService.findById(id));
        return "description";
    }

    /**
     * Редактировать задачу по ID.
     */
    @GetMapping("/editTask/{taskId}")
    public String editTask(Model model, @PathVariable("taskId") int id, HttpSession session) {
        setUser(model, session);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("task", taskService.findById(id));
        return "editTask";
    }

    /**
     * Редактировать задачу.
     */
    @PostMapping("/editTask")
    public String editTask(@ModelAttribute Task task,
                           @RequestParam Set<Integer> categoryID,
                           HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        task.setCategories(categoryService.getCategories(categoryID));
        taskService.update(task);
        return "redirect:/description/" + task.getId();
    }

    /**
     * Удалить задачу по ID.
     */
    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id, HttpSession session) {
        taskService.delete(id);
        return "redirect:/allTasks";
    }

    /**
     * Отметить задачу как выполненную.
     */
    @GetMapping("/setDone/{taskId}")
    public String setDone(Model model, @PathVariable("taskId") int id, HttpSession session) {
        setUser(model, session);
        taskService.setDoneById(id);
        return "redirect:/description/{taskId}";
    }

    /**
     * Отметить задачу как активную.
     */
    @GetMapping("/setActive/{taskId}")
    public String setActive(Model model, @PathVariable("taskId") int id, HttpSession session) {
        setUser(model, session);
        taskService.setActiveById(id);
        return "redirect:/description/{taskId}";
    }

    /**
     * Получить список активных задач.
     */
    @GetMapping("/showActive")
    public String showActive(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute(TASKS, taskService.findActive());
        return "showActive";
    }

    /**
     * Получить список выполненных задач.
     */
    @GetMapping("/showDone")
    public String showDone(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute(TASKS, taskService.findDone());
        return "showDone";
    }
}
