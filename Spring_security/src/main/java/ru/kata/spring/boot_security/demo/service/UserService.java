package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

/**
 * Интерфейс сервисного слоя для управления пользователями.
 * Определяет контракт для реализации бизнес-логики, связанной с пользователями и их ролями.
 * Используется в контроллерах для отделения логики от веб-уровня.
 */
public interface UserService {

    /**
     * Получить список всех пользователей из базы данных.
     */
    List<User> findAll();

    /**
     * Найти пользователя по его уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     * @throws RuntimeException если пользователь не найден
     */
    User findById(Long id);

    /**
     * Сохранить нового пользователя в базу данных.
     * @param dto объект передачи данных пользователя
     */
    void saveWithRoles(UserDto dto);

    /**
     * Обновить существующего пользователя.
     * @param dto объект передачи данных пользователя
     */
    void updateWithRoles(UserDto dto);

    /**
     * Удалить пользователя по его идентификатору.
     * @param id идентификатор пользователя
     */
    void deleteById(Long id);

    /**
     * Получить список всех доступных ролей в системе.
     * @return список всех ролей
     */
    List<Role> findAllRoles();
}
