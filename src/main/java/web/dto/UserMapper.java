package web.dto;

import org.springframework.stereotype.Component;
import web.model.User;

/**
 * Компонент для преобразования сущностей {@link User} в DTO и обратно.
 * <p>
 * Инкапсулирует логику маппинга между слоями persistence и service/controller.
 */
@Component
public class UserMapper {

    /**
     * Преобразует сущность User в UserResponseDto.
     */
    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    /**
     * Преобразует UserRequestDto в сущность User для сохранения в БД.
     */
    public User toUser(UserRequestDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }
}