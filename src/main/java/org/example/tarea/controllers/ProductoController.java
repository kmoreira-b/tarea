package org.example.tarea.controllers;

import org.example.tarea.models.dtos.ProductoDTO;
import org.example.tarea.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> buscarProducto(@PathVariable Long id) {
        return productoService.buscarProducto(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO unProducto) {
        return productoService.crearProducto(unProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO unProducto) {
        return productoService.actualizarProducto(id, unProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarProducto(@PathVariable Long id) {
        return productoService.borrarProducto(id);
    }
}
