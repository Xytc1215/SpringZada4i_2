package web.exception;

/**
 * Исключение выбрасывается, если пользователь не найден в базе по id.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id = " + id + " not found");
    }
}
