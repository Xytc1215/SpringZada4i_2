package ru.kata.spring.boot_security.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO для передачи данных пользователя во внешних слоях.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 50, message = "Максимум 50 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 50)
    private String lastName;

    @Min(value = 0, message = "Возраст должен быть неотрицательным")
    private int age;

    @NotBlank @Size(max = 50)
    private String username;

    @Email(message = "Некорректный email")
    @NotBlank
    private String email;

    @NotBlank @Size(min = 6, message = "Пароль минимум 6 символов")
    private String password;

    @NotEmpty(message = "Выберите хотя бы одну роль")
    private String[] roles;
}
