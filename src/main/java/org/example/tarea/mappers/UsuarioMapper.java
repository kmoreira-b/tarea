package org.example.tarea.mappers;

import org.example.tarea.models.dtos.UsuarioDTO;
import org.example.tarea.models.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "rol.nombre", target = "rol")
    UsuarioDTO usuarioToDto(Usuario usuario);
}
