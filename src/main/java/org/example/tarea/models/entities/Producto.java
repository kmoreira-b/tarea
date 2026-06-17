package org.example.tarea.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    String nombre;

    @Column(name = "descripcion")
    String descripcion;

    @Column(name = "precio")
    BigDecimal precio;

    @Column(name = "cantidad_en_stock")
    Integer cantidadEnStock;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    Categoria categoria;
}
