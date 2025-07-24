package web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для создания или обновления пользователя.
 * Используется для получения данных из формы и их валидации.
 */
@Data
public class UserRequestDto {

    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    private String name;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;
}