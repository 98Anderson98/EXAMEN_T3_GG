package com.ecommerce.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para crear / actualizar productos.
 */
public record ProductoRequestDTO(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombre,

        @NotBlank(message = "La categoria es obligatoria")
        @Size(max = 50)
        String categoria,

        @NotNull(message = "El precio de compra es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor a 0")
        BigDecimal precioCompra,

        @NotNull(message = "El precio de venta es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
        BigDecimal precioVenta,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Size(max = 50)
        String marca,

        @NotBlank(message = "El codigo de barras es obligatorio")
        @Size(max = 50)
        String codigoBarras,

        @NotNull(message = "La fecha de ingreso es obligatoria")
        LocalDate fechaIngreso
) {
}
