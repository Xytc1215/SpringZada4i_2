package ru.kata.spring.boot_security.demo.dto;

import lombok.*;

/**
 * DTO для роли.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
}
