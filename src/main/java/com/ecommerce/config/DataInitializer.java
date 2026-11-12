package com.ecommerce.config;

import com.ecommerce.entity.Producto;
import com.ecommerce.entity.Rol;
import com.ecommerce.entity.Usuario;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Crea los usuarios y productos iniciales si no existen (password encriptado con BCrypt).
 *   admin    / admin123     (ROLE_ADMIN)
 *   anderson / anderson123  (ROLE_ADMIN)  -> usuario con tu nombre
 *   invitado / invitado123  (ROLE_USER)
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           ProductoRepository productoRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crear("admin", "admin123", "Administrador del Sistema", Rol.ADMIN);
        crear("anderson", "anderson123", "Anderson Fabrizzio Larios Tito", Rol.ADMIN);
        crear("invitado", "invitado123", "Usuario Invitado", Rol.USER);

        if (productoRepository.count() == 0) {
            crearProducto("Laptop IdeaPad 3", "Computo", "1800.00", "2299.90", 15, "Lenovo", "7751234000011", "2026-10-01");
            crearProducto("Mouse inalambrico M185", "Accesorios", "35.00", "59.90", 80, "Logitech", "7751234000028", "2026-10-03");
            crearProducto("Teclado mecanico K552", "Accesorios", "120.00", "179.90", 40, "Redragon", "7751234000035", "2026-10-05");
            crearProducto("Monitor 24 pulgadas", "Computo", "450.00", "649.90", 20, "Samsung", "7751234000042", "2026-10-08");
            crearProducto("Audifonos WH-CH520", "Audio", "150.00", "229.90", 8, "Sony", "7751234000059", "2026-10-10");
            crearProducto("Smartphone Galaxy A15", "Celulares", "600.00", "849.90", 25, "Samsung", "7751234000066", "2026-10-12");
            log.info("Productos iniciales cargados correctamente (6 registros)");
        }
    }

    private void crear(String username, String password, String nombre, Rol rol) {
        if (!usuarioRepository.existsByUsername(username)) {
            Usuario u = new Usuario();
            u.setUsername(username);
            u.setPassword(passwordEncoder.encode(password));
            u.setNombreCompleto(nombre);
            u.setRol(rol);
            u.setActivo(true);
            usuarioRepository.save(u);
            log.info("Usuario inicial creado: {}", username);
        }
    }

    private void crearProducto(String nombre, String categoria, String precioCompra, String precioVenta,
                               int stock, String marca, String codigoBarras, String fechaIngreso) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setPrecioCompra(new BigDecimal(precioCompra));
        p.setPrecioVenta(new BigDecimal(precioVenta));
        p.setStock(stock);
        p.setMarca(marca);
        p.setCodigoBarras(codigoBarras);
        p.setFechaIngreso(LocalDate.parse(fechaIngreso));
        productoRepository.save(p);
    }
}
