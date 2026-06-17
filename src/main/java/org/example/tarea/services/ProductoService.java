package org.example.tarea.services;

import org.example.tarea.mappers.ProductoMapper;
import org.example.tarea.models.dtos.ProductoDTO;
import org.example.tarea.models.entities.Categoria;
import org.example.tarea.models.entities.Producto;
import org.example.tarea.repos.CategoriaRepository;
import org.example.tarea.repos.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    public ResponseEntity<?> crearProducto(ProductoDTO unProducto) {
        Optional<Categoria> categoria = categoriaRepository.findById(unProducto.getCategoriaId());
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().body("La categoria indicada no existe");
        }
        Producto producto = productoMapper.productoDtoToProducto(unProducto);
        producto.setId(null);
        producto.setCategoria(categoria.get());
        return ResponseEntity.ok(productoMapper.productoToDto(productoRepository.save(producto)));
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::productoToDto)
                .toList();
    }

    public ResponseEntity<?> actualizarProducto(Long id, ProductoDTO unProducto) {
        Optional<Producto> existente = productoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Categoria> categoria = categoriaRepository.findById(unProducto.getCategoriaId());
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().body("La categoria indicada no existe");
        }
        Producto producto = existente.get();
        producto.setNombre(unProducto.getNombre());
        producto.setDescripcion(unProducto.getDescripcion());
        producto.setPrecio(unProducto.getPrecio());
        producto.setCantidadEnStock(unProducto.getCantidadEnStock());
        producto.setCategoria(categoria.get());
        return ResponseEntity.ok(productoMapper.productoToDto(productoRepository.save(producto)));
    }

    public ResponseEntity<ProductoDTO> buscarProducto(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoMapper.productoToDto(producto.get()));
    }

    public ResponseEntity<String> borrarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.ok("Se borró el producto con id " + id);
    }
}
