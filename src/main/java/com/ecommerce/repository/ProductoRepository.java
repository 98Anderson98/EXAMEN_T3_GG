package com.ecommerce.repository;

import com.ecommerce.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA.
 * - Los metodos listarTodos, buscarPorCategoria, buscarPorNombre, buscarPorCodigoBarras y stockBajo
 *   se resuelven automaticamente con las @NamedQuery definidas en la entidad Producto
 *   (Spring Data busca "Producto.nombreDelMetodo").
 * - Inyeccion segura JPQL: TODOS los valores se pasan con parametros nombrados (:param),
 *   nunca concatenando Strings, asi se evita la inyeccion SQL/JPQL.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // ---- Named Queries (definidas en la entidad) ----
    List<Producto> listarTodos();

    List<Producto> buscarPorCategoria(@Param("categoria") String categoria);

    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    Optional<Producto> buscarPorCodigoBarras(@Param("codigoBarras") String codigoBarras);

    List<Producto> stockBajo(@Param("minimo") Integer minimo);

    // ---- JPQL con parametros seguros ----
    @Query("SELECT p FROM Producto p WHERE p.marca = :marca AND p.precioVenta <= :precioMax")
    List<Producto> buscarPorMarcaYPrecioMax(@Param("marca") String marca,
                                            @Param("precioMax") java.math.BigDecimal precioMax);

    // ---- Derivados de Spring Data (tambien parametrizados) ----
    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);
}
