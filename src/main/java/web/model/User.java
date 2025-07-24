package web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * JPA-сущность, представляющая пользователя в системе.
 * Эта модель отображается на таблицу 'users' в базе данных.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Уникальный идентификатор пользователя. Генерируется автоматически базой данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя. Поле не может быть пустым и должно иметь длину от 2 до 50 символов.
     */
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String name;

    /**
     * Электронная почта пользователя. Используется для входа и является уникальной.
     * Поле не может быть пустым и должно соответствовать формату email.
     */
    @Email(message = "Неверный формат email")
    @NotBlank(message = "Email не должен быть пустым")
    @Column(nullable = false, unique = true)
    private String email;
}
