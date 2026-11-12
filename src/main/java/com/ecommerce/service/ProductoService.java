package com.ecommerce.service;

import com.ecommerce.dto.ProductoRequestDTO;
import com.ecommerce.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoResponseDTO> listar();

    ProductoResponseDTO obtenerPorId(Long id);

    ProductoResponseDTO crear(ProductoRequestDTO dto);

    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto);

    void eliminar(Long id);

    List<ProductoResponseDTO> buscarPorCategoria(String categoria);

    List<ProductoResponseDTO> buscarPorNombre(String nombre);

    List<ProductoResponseDTO> stockBajo(Integer minimo);
}
