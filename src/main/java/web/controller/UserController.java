package web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.dto.UserRequestDto;
import web.dto.UserResponseDto;
import web.exception.UserNotFoundException;
import web.service.UserService;


/**
 * Контроллер для управления CRUD‑операциями над пользователями.
 * <p>
 * Обрабатывает HTTP‑запросы на показ списка, создание, обновление и удаление пользователей,
 * взаимодействуя с сервисным слоем {@link UserService}.
 * </p>
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Показать страницу со списком всех пользователей.
     */
    @GetMapping
    public String listUsers(Model model) {
        log.info("GET /users - displaying list of all users");
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    /**
     * Показать форму для создания нового пользователя.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("GET /users/add - showing form to create a new user");
        model.addAttribute("userRequest", new UserRequestDto());
        return "users/form";
    }

    /**
     * Обработать отправку формы создания пользователя.
     * <p>
     * Если валидация проходит штатно, сохраняет пользователя и делает редирект на список.
     * При ошибке валидации возвращает ту же форму с сообщениями об ошибках.
     */
    @PostMapping
    public String createUser(@ModelAttribute("userRequest") @Valid UserRequestDto userRequest,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("POST /users - validation errors: {}", bindingResult.getAllErrors());
            return "users/form";
        }
        userService.saveUser(userRequest);
        log.info("POST /users - successfully created user");
        redirectAttributes.addFlashAttribute("successMessage", "User added successfully!");
        return "redirect:/users";
    }

    /**
     * Показать форму редактирования существующего пользователя.
     * <p>
     * Загружает из БД DTO ответа, мапит в DTO запроса и передаёт во view.
     * При отсутствии пользователя — редирект на список с ошибкой.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("GET /users/edit/{} - showing form to edit user", id);
        try {
            UserResponseDto userResponse = userService.getUserById(id);
            UserRequestDto userRequest = new UserRequestDto();
            userRequest.setId(userResponse.getId());
            userRequest.setName(userResponse.getName());
            userRequest.setEmail(userResponse.getEmail());
            model.addAttribute("userRequest", userRequest);
            return "users/form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users";
        }
    }

    /**
     * Обработать отправку формы обновления пользователя.
     * <p>
     * Проверяет валидацию, обновляет через сервис и делает редирект.
     * В случае отсутствия в БД — показывает flash‑ошибку.
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("userRequest")
    @Valid UserRequestDto userRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("POST /users/update/{} - validation errors", id);
            userRequest.setId(id);
            return "users/form";
        }
        try {
            userService.updateUser(id, userRequest);
            log.info("POST /users/update/{} - successfully updated user", id);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    /**
     * Удалить пользователя по идентификатору.
     * <p>
     * При успешном удалении — flash‑сообщение об успехе,
     * при отсутствии пользователя — flash‑ошибка.
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            log.info("GET /users/delete/{} - successfully deleted user", id);
            redirectAttributes.addFlashAttribute("successMessage", "User successfully deleted.");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
}