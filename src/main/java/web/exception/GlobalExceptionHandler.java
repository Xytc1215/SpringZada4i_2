package web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Глобальный обработчик исключений для всего приложения.
 * Перехватывает определённые исключения из любого контроллера
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link UserNotFoundException}.
     * Устанавливает сообщение об ошибке и перенаправляет пользователя на главную страницу.
     * @param ex Перехваченное исключение.
     * @param redirectAttributes Атрибуты для передачи сообщения при редиректе.
     * @return Строка редиректа.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.warn("Handled UserNotFoundException: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/users";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Unhandled exception", ex);
        model.addAttribute("errorMessage", "Что-то пошло не так. Пожалуйста, повторите попытку позже.");
        return "error";
    }
}
