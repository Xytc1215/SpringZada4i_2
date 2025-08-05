package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

/**
 * Ищем пользователя сначала по username, потом — по email.
 * Форму логина мы будем отправлять поле emailOrUsername.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername)
            throws UsernameNotFoundException {

        User user = userRepo.findByUsername(emailOrUsername)
                .or(() -> userRepo.findByEmail(emailOrUsername))
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Пользователь не найден: " + emailOrUsername)
                );

        return user;
    }
}
