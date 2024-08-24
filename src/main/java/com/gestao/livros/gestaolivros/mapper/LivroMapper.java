package com.gestao.livros.gestaolivros.mapper;

import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    LivroDto toDto(LivroEntity livroEntity);

    LivroEntity toEntity(LivroDto livroDto);

}
