package ru.kata.spring.boot_security.demo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.service.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

/**
 * Конфигурация Spring Security: настройка аутентификации, авторизации и поведения при логине/логауте.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImpl userDetailsService;

    /** Шифрование паролей. */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** DAO-провайдер с кастомным UserDetailsService и шифрованием паролей. */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // 🔄 тут наш сервис из БД
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Делает AuthenticationManager доступным для других бинов.
     */
    @Bean
    public AuthenticationConfiguration authenticationConfiguration() {
        return new AuthenticationConfiguration();
    }

    /**
     * Основная настройка фильтров безопасности:
     * 1) Регистрирует наш DaoAuthenticationProvider.
     * 2) Задаёт права доступа по URL.
     * 3) Настраивает кастомную форму логина и логаута.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/login", "/error/**", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("emailOrUsername")
                        .passwordParameter("password")
                        .successHandler(successUserHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
