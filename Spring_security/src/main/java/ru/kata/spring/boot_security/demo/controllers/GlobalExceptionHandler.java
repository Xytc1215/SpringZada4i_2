package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.stream.Collectors;

/**
 * Глобальный обработчик ошибок:
 * – валидации (@Valid)
 * – доступа (403)
 * – прочие (500)
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /** Обработка ошибок валидации DTO. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String onValidationException(MethodArgumentNotValidException ex, Model model) {
        BindingResult result = ex.getBindingResult();
        String errors = result.getFieldErrors().stream()
                .map(f -> f.getField()+": "+f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        model.addAttribute("errorMessage", "Ошибка валидации: " + errors);
        return "error";
    }

    /** Обработка 403 Forbidden. */
    @ExceptionHandler(AccessDeniedException.class)
    public String onAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("errorMessage", "Доступ запрещён: " + ex.getMessage());
        return "403";
    }

    /** Любые другие исключения. */
    @ExceptionHandler(Exception.class)
    public String onAnyException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
