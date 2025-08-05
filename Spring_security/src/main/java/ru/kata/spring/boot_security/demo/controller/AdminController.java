package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.service.UserService;

import jakarta.validation.Valid;
import java.util.stream.Collectors;

/**
 * Контроллер для управления пользователями.
 * Доступен только администраторам.
 */
@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final UserService userService;
    private final UserMapper userMapper;

    /** Показывает список всех пользователей. */
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
        return "list";
    }

    /** Показывает форму добавления нового пользователя. */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", userService.findAllRoles()
                .stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "add";
    }

    /** Обрабатывает сохранение нового пользователя с валидацией DTO. */
    @PostMapping("/add")
    public String saveUser(@ModelAttribute("userDto") @Valid UserDto dto) {
        userService.saveWithRoles(dto);
        return "redirect:/admin";
    }

    /** Показывает форму редактирования пользователя. */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        UserDto dto = userMapper.toDto(userService.findById(id));
        model.addAttribute("userDto", dto);
        model.addAttribute("roles", userService.findAllRoles()
                .stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "edit";
    }

    /** Обрабатывает обновление пользователя. */
    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("userDto") @Valid UserDto dto) {
        userService.updateWithRoles(dto);
        return "redirect:/admin";
    }

    /** Обрабатывает удаление пользователя. */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
