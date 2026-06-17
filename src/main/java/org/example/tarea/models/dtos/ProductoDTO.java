package org.example.tarea.models.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    String nombre;
    String descripcion;
    BigDecimal precio;
    Integer cantidadEnStock;
    Long categoriaId;
}
