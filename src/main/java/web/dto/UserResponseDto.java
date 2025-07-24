package web.dto;

import lombok.Data;

/**
 * DTO для отображения информации о пользователе.
 * Используется для передачи данных из бэкенда в представление (view).
 */
@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
}