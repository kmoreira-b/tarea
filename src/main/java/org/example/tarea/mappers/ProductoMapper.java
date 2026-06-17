package org.example.tarea.mappers;

import org.example.tarea.models.dtos.ProductoDTO;
import org.example.tarea.models.entities.Categoria;
import org.example.tarea.models.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    @Mapping(target = "categoriaId", source = "categoria")
    ProductoDTO productoToDto(Producto producto);

    @Mapping(target = "categoria", ignore = true)
    Producto productoDtoToProducto(ProductoDTO producto);

    default Long map(Categoria value) {
        return value == null ? null : value.getId();
    }
}
