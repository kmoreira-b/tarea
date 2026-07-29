package org.example.tarea.controllers;

import org.example.tarea.models.dtos.CategoriaDTO;
import org.example.tarea.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Mismas reglas que ProductoController: consultar esta abierto a cualquier
 * usuario con sesion, y escribir pide SUPER-ADMIN-ROLE.
 */
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoria(@PathVariable Long id) {
        return categoriaService.buscarCategoria(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO unaCategoria) {
        return categoriaService.crearCategoria(unaCategoria);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO unaCategoria) {
        return categoriaService.actualizarCategoria(id, unaCategoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<String> borrarCategoria(@PathVariable Long id) {
        return categoriaService.borrarCategoria(id);
    }
}
