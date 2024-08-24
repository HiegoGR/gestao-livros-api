package com.gestao.livros.gestaolivros.repository;

import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {

    //Verificar se o livro já está emprestado, caso estiver retorna true caso contrario false
    boolean existsByLivroIdAndStatusIsTrue(Long livroId);

    //Buscar categorias distintas dos livros que o usuário já pegou emprestado
    @Query(value = "select " +
            "        distinct(liv.id) as categoria_livro_id  \n" +
            "   from emprestimo emp\n" +
            "   join livro liv on (liv.id = emp.livro_id)\n" +
            "where emp.usuario_id =:usuarioId", nativeQuery = true)
    List<Long> findDistinctCategoriaByUsuarioId(@Param("usuarioId") Long usuarioId);

    //Buscar IDs dos livros que o usuário já pegou emprestado
    @Query(value = "select * \n" +
            "   from emprestimo emp\n" +
            "where emp.usuario_id =:usuarioId", nativeQuery = true)
    List<Long> findLivroIdsByUsuarioId(@Param("usuarioId") Long usuarioId);
}
