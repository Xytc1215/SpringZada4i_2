package web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.UserMapper;
import web.dto.UserRequestDto;
import web.dto.UserResponseDto;
import web.exception.UserNotFoundException;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация {@link UserService}.
 * Содержит бизнес‑логику для операций над пользователями
 * и работает в рамках транзакций Spring (@Transactional).
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Получает список всех пользователей в виде DTO.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        log.debug("Service: fetching all users and mapping to DTO");
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает пользователя по идентификатору и преобразует в DTO.
     *
     * @param id идентификатор пользователя
     * @return {@link UserResponseDto} с данными пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        log.debug("Service: fetching user by id {} and mapping to DTO", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDto(user);
    }

    /**
     * Сохраняет нового пользователя на основе данных из DTO.
     *
     * @param userDto DTO с данными нового пользователя
     */
    @Override
    @Transactional
    public void saveUser(UserRequestDto userDto) {
        log.debug("Service: mapping DTO and saving new user");
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
    }

    /**
     * Обновляет существующего пользователя по ID на основе данных из DTO.
     */
    @Override
    @Transactional
    public void updateUser(Long id, UserRequestDto userDto) {
        log.debug("Service: updating user with id {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        userRepository.save(existingUser);
    }

    /**
     * Удаляет пользователя по идентификатору.
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.debug("Service: deleting user with id={}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}