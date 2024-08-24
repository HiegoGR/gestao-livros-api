package com.gestao.livros.gestaolivros.repository;

import com.gestao.livros.gestaolivros.entities.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    //Buscar livros com base nos interesses anteriores do usuário
    @Query(value = "select *\n" +
            "  from livro liv\n" +
            "where liv.categoria in (\n" +//seleciona todas as categorias dos livros que foram pegos emprestados pelo usuário
            "                        select distinct liv2.categoria\n" +
            "                            from livro liv2\n" +
            "                            join emprestimo emp on liv2.id = emp.livro_id\n" +
            "                        where emp.usuario_id =:usuarioId\n" +
            "                        )\n" +
            "and liv.id not in (\n" +//seleciona todos os IDs dos livros que foram pegos emprestados pelo usuário
            "                    select liv3.id\n" +
            "                       from livro liv3\n" +
            "                       join emprestimo emp3 on liv3.id = emp3.livro_id\n" +
            "                    where emp3.usuario_id =:usuarioId\n" +
            "                    );\n",
            nativeQuery = true)
    List<LivroEntity> findRecomendacoesByUsuarioId(@Param("usuarioId") Long usuarioId);

}
