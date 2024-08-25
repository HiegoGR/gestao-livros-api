package com.gestao.livros.gestaolivros.mapper;


import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import com.gestao.livros.gestaolivros.entities.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    EmprestimoEntity toEntity(EmprestimoDto dto);

    EmprestimoDto toDto(EmprestimoEntity entity);

    // Método para converter Entidade para DTO
    public default EmprestimoDto convertToDTO(EmprestimoEntity emprestimoEntity) {
        return new EmprestimoDto(
                emprestimoEntity.getId(),
                emprestimoEntity.getUsuario().getId(),
                emprestimoEntity.getLivro().getId(),
                emprestimoEntity.getDataEmprestimo(),
                emprestimoEntity.getDataDevolucao(),
                emprestimoEntity.getStatus()
        );
    }

    // Método para converter DTO para Entidade
    public default EmprestimoEntity convertToEntity(EmprestimoDto emprestimoDTO) {
        EmprestimoEntity emprestimoEntity = new EmprestimoEntity();
        emprestimoEntity.setId(emprestimoDTO.getId());
        emprestimoEntity.setUsuario(new UsuarioEntity(emprestimoDTO.getIdUsuario()));
        emprestimoEntity.setLivro(new LivroEntity(emprestimoDTO.getIdLivro()));
        emprestimoEntity.setDataEmprestimo(emprestimoDTO.getDataEmprestimo());
        emprestimoEntity.setDataDevolucao(emprestimoDTO.getDataDevolucao());
        emprestimoEntity.setStatus(emprestimoDTO.getStatus());
        return emprestimoEntity;
    }

    public default EmprestimoDto toDto2(EmprestimoEntity emprestimo) {
        return new EmprestimoDto(
                emprestimo.getId(),
                emprestimo.getUsuario() != null ? emprestimo.getUsuario().getId() : null,
                emprestimo.getLivro() != null ? emprestimo.getLivro().getId() : null,
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatus()
        );
    }

}
