package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.User;

/**
 * Репозиторий для CRUD‑операций над сущностью {@link User}.
 * <p>
 * Расширяет {@link JpaRepository}, чтобы автоматически получить
 * методы сохранения, поиска, удаления и т.д.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}