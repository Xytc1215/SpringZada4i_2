package web.service;

import web.dto.UserRequestDto;
import web.dto.UserResponseDto;
import java.util.List;

/**
 * Сервисный интерфейс для управления пользователями.
 * <p>
 * Определяет основные бизнес‑операции: получение списка, поиск по id,
 * создание, обновление и удаление.
 */
public interface UserService {

    /**
     * Получить всех пользователей.
     */
    List<UserResponseDto> getAllUsers();

    /**
     * Найти пользователя по идентификатору.
     */
    UserResponseDto getUserById(Long id);

    /**
     * Сохранить нового пользователя.
     */
    void saveUser(UserRequestDto userDto);

    /**
     * Обновить данные существующего пользователя.
     */
    void updateUser(Long id, UserRequestDto userDto);

    /**
     * Удалить пользователя по идентификатору.
     */
    void deleteUser(Long id);
}