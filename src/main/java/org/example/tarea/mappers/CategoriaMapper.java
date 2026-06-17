package org.example.tarea.mappers;

import org.example.tarea.models.dtos.CategoriaDTO;
import org.example.tarea.models.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    CategoriaDTO categoriaToDto(Categoria categoria);

    Categoria categoriaDtoToCategoria(CategoriaDTO categoria);
}
