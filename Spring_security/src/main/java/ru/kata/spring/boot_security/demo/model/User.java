package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

/**
 * Сущность пользователя, реализует Spring Security UserDetails.
 */
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;

    /** Логин пользователя, уникален. */
    @Column(unique = true, nullable = false)
    private String username;

    /** Email пользователя, уникален. */
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    /** Зашифрованный пароль. */
    @Column(nullable = false)
    private String password;

    /** Роли пользователя, выгружаем EAGER, чтобы сразу были в контексте безопасности. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // --- Реализация UserDetails ---

    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}