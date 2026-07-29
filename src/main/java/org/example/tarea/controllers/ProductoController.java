package org.example.tarea.controllers;

import org.example.tarea.models.dtos.ProductoDTO;
import org.example.tarea.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Consultar productos esta abierto a cualquier usuario con sesion; registrar,
 * actualizar y borrar piden SUPER-ADMIN-ROLE. El permiso se declara con
 * @PreAuthorize en cada metodo para que la regla se lea junto al endpoint que
 * protege.
 */
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
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO unProducto) {
        return productoService.crearProducto(unProducto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO unProducto) {
        return productoService.actualizarProducto(id, unProducto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<String> borrarProducto(@PathVariable Long id) {
        return productoService.borrarProducto(id);
    }
}
