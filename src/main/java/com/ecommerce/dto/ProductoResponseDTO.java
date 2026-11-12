package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
