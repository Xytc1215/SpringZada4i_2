package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Общий контроллер для публичных страниц.
 */
@Controller
public class MainController {

    /** Главная (публичная) страница по URL: / и /index. */
    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }
}
