package org.example.tarea.models.dtos;

import lombok.*;

/**
 * Datos de un usuario que se exponen al frontend.
 * No incluye el password: el hash nunca sale del backend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    String username;
    String rol;
}
