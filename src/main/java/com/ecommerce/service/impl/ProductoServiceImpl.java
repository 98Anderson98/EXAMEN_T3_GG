package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductoRequestDTO;
import com.ecommerce.dto.ProductoResponseDTO;
import com.ecommerce.entity.Producto;
import com.ecommerce.exception.RecursoNoEncontradoException;
import com.ecommerce.exception.ReglaNegocioException;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listar() {
        return productoRepository.listarTodos().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        return toDTO(buscarEntidad(id));
    }

    @Override
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        validarReglas(dto);
        if (productoRepository.existsByCodigoBarras(dto.codigoBarras())) {
            throw new ReglaNegocioException("Ya existe un producto con el codigo de barras " + dto.codigoBarras());
        }
        Producto producto = new Producto();
        copiar(dto, producto);
        return toDTO(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = buscarEntidad(id);
        validarReglas(dto);
        if (productoRepository.existsByCodigoBarrasAndIdNot(dto.codigoBarras(), id)) {
            throw new ReglaNegocioException("Ya existe otro producto con el codigo de barras " + dto.codigoBarras());
        }
        copiar(dto, producto);
        return toDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = buscarEntidad(id);
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {
        return productoRepository.buscarPorCategoria(categoria).stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.buscarPorNombre(nombre).stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> stockBajo(Integer minimo) {
        return productoRepository.stockBajo(minimo).stream().map(this::toDTO).toList();
    }

    private Producto buscarEntidad(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id " + id));
    }

    private void validarReglas(ProductoRequestDTO dto) {
        if (dto.precioVenta().compareTo(dto.precioCompra()) < 0) {
            throw new ReglaNegocioException("El precio de venta no puede ser menor al precio de compra");
        }
    }

    private void copiar(ProductoRequestDTO dto, Producto p) {
        p.setNombre(dto.nombre());
        p.setCategoria(dto.categoria());
        p.setPrecioCompra(dto.precioCompra());
        p.setPrecioVenta(dto.precioVenta());
        p.setStock(dto.stock());
        p.setMarca(dto.marca());
        p.setCodigoBarras(dto.codigoBarras());
        p.setFechaIngreso(dto.fechaIngreso());
    }

    private ProductoResponseDTO toDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(), p.getNombre(), p.getCategoria(),
                p.getPrecioCompra(), p.getPrecioVenta(), p.getStock(),
                p.getMarca(), p.getCodigoBarras(), p.getFechaIngreso());
    }
}
