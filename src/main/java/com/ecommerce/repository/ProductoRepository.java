package com.ecommerce.repository;

import com.ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> listarTodos();

    List<Producto> buscarPorCategoria(@Param("categoria") String categoria);

    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    Optional<Producto> buscarPorCodigoBarras(@Param("codigoBarras") String codigoBarras);

    List<Producto> stockBajo(@Param("minimo") Integer minimo);

    @Query("SELECT p FROM Producto p WHERE p.marca = :marca AND p.precioVenta <= :precioMax")
    List<Producto> buscarPorMarcaYPrecioMax(@Param("marca") String marca,
                                            @Param("precioMax") java.math.BigDecimal precioMax);

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);
}
