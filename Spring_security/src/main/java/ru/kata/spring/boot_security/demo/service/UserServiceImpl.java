package ru.kata.spring.boot_security.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.mapper.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервисная реализация работы с пользователями.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    /** Найти всех пользователей. */
    @Override
    public List<User> findAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    /** Найти пользователя по ID. */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found, id=" + id));
    }

    /** Сохранить нового пользователя, маппинг из DTO + роли. */
    @Transactional
    @Override
    public void saveWithRoles(UserDto dto) {
        User user = userMapper.fromDto(dto);
        Set<Role> roleSet = getRoleSet(dto.getRoles());
        user.setRoles(roleSet);
        userRepository.save(user);
        log.info("User created: {}", user.getUsername());
    }

    /** Обновить пользователя, включая роли. */
    @Transactional
    @Override
    public void updateWithRoles(UserDto dto) {
        User user = userMapper.fromDto(dto);
        Set<Role> roleSet = getRoleSet(dto.getRoles());
        user.setRoles(roleSet);
        userRepository.save(user);
        log.info("User updated: {}", user.getUsername());
    }

    /** Удалить пользователя по ID. */
    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        log.info("User with id has been deleted={}", id);
    }

    /** Найти все роли. */
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    /** Получить набор ролей по именам. */
    private Set<Role> getRoleSet(String[] roleNames) {
        return List.of(roleNames).stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet());
    }
}
