package com.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "El username es obligatorio") String username,
        @NotBlank(message = "El password es obligatorio") String password
) {
}
