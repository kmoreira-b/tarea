package org.example.tarea.controllers;

import org.example.tarea.models.dtos.UsuarioDTO;
import org.example.tarea.services.UsuarioService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Consulta de los usuarios registrados.
 *
 * El @PreAuthorize va en la clase, no en el metodo.
 */
@RestController
@RequestMapping("/api/usuario")
@PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}
