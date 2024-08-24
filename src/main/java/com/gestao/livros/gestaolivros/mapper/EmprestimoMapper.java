package com.gestao.livros.gestaolivros.mapper;

import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    EmprestimoDto toDto(EmprestimoEntity emprestimoEntity);

    EmprestimoEntity toEntity(EmprestimoDto emprestimoDto);
}
