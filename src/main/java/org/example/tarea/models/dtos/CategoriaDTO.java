package org.example.tarea.models.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;
    String nombre;
    String descripcion;
}
