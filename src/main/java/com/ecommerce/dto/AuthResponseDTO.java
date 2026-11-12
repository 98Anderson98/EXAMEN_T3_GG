package com.ecommerce.dto;

public record AuthResponseDTO(
        String token,
        String tipo,
        String username,
        String rol
) {
}
