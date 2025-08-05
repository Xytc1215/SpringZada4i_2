package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация Spring MVC для простого сопоставления URL-адресов в шаблоны.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Регистрирует URL → имя шаблона:
     * - /login → login.html
     * - /user  → user.html
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/login").setViewName("login");
    }
}