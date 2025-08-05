# Spring Boot Security Demo

Демонстрационный проект на Spring Boot с реализацией:

- аутентификации и авторизации через Spring Security + MySQL
- CRUD‑операций над пользователями с ролями
- DTO/Entity маппинга через MapStruct
- Bean Validation и централизованной обработки ошибок
- Thymeleaf + Bootstrap для UI
- SLF4J‑логирования и метрик через Actuator/Micrometer

---

## 📋 Задание

1. Перенести классы и зависимости из предыдущей задачи.
2. Создать сущность `Role` и связать `User` с ролями (многие‑ко‑многим).
3. Реализовать `Role` → `GrantedAuthority`, `User` → `UserDetails`; переключиться с in‑memory на `UserDetailsService`.
4. Разрешить все CRUD‑операции только для `/admin/**` (роль `ADMIN`).
5. Доступ к `/user` — только для ролей `USER` и `ADMIN`.
6. Настроить logout через Thymeleaf‑форму на любой странице.
7. Реализовать `LoginSuccessHandler`: `ROLE_ADMIN` → `/admin`, `ROLE_USER` → `/user`.

---

## 🔍 Обзор возможностей

1. **Аутентификация и авторизация**
    - Spring Security + DAO‑аутентификация (MySQL)
    - Логин по `username` или `email`
    - Хранение пользователей и ролей в БД
    - Ограничение доступа `/admin/**`, `/user`

2. **CRUD‑операции**
    - Панель администратора на `/admin`
    - Пользователь может иметь несколько ролей
    - DTO (`UserDto`, `RoleDto`) + MapStruct

3. **Валидация и обработка ошибок**
    - Bean Validation (`@NotBlank`, `@Email`, `@Size` и т.д.)
    - Глобальный `@ControllerAdvice` для 400, 403, 500
    - Кастомные страницы `error.html`, `403.html`, `404.html`

4. **UI: Thymeleaf + Bootstrap 5**
    - Форма логина, админка, кабинет пользователя
    - Фрагмент `header.html` с logout‑формой

5. **Логирование и метрики**
    - SLF4J через Lombok `@Slf4j`
    - Spring Boot Actuator + Micrometer (Prometheus)
    - Конфигурирование уровней логирования

---

## 🛠 Технологии и зависимости

- **Java 17**, **Spring Boot 3.5.3**
- **Spring Security**, **Spring Data JPA**, **Hibernate**, **MySQL Connector/J**
- **Thymeleaf 3.1**, `thymeleaf-extras-springsecurity6`
- **Jakarta Bean Validation**, `spring-boot-starter-validation`
- **MapStruct 1.5**, **Lombok**
- **SLF4J/Logback**, **Spring Boot Actuator**, **Micrometer**
- **Bootstrap 5**

---

## 🚀 Быстрый старт

### 1. Стек

- Java 17
- Maven 3.8+
- MySQL 8+

### 2. Настройка базы данных

```sql
CREATE DATABASE spring_crud CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'springcourse';
FLUSH PRIVILEGES;
