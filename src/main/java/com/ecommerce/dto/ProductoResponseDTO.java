package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de salida de productos.
 */
public record ProductoResponseDTO(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        Integer stock,
        String marca,
        String codigoBarras,
        LocalDate fechaIngreso
) {
}
