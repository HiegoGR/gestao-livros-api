package com.gestao.livros.gestaolivros.mapper;

import com.gestao.livros.gestaolivros.dto.UsuarioDto;
import com.gestao.livros.gestaolivros.entities.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toDto(UsuarioEntity usuarioEntity);

    UsuarioEntity toEntity(UsuarioDto usuarioDto);
}
