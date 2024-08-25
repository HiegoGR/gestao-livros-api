package com.gestao.livros.gestaolivros.repository;


import com.gestao.livros.gestaolivros.dto.EmprestimoHistoricoLivrosUsuario;
import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.service.Dao;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoricoLivrosDao extends Dao {

    @Transactional
    public List<EmprestimoHistoricoLivrosUsuario> historicoDeLivrosPegosPorUsuario(Long idUser) {
        String sql =
                "select emp.id as emprestimo_id, " +
                        "       emp.status, " +
                        "       us.id as user_id, " +
                        "       us.nome as user_nome, " +
                        "       us.email, " +
                        "       liv.id as livro_id, " +
                        "       liv.titulo, " +
                        "       liv.categoria " +
                        "from emprestimo emp " +
                        "join usuario us on us.id = emp.usuario_id " +
                        "join livro liv on liv.id = emp.livro_id " +
                        "where us.id =:usuarioId";

        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter("usuarioId", idUser);

        List<Object[]> result = sqlQuery.list();
        List<EmprestimoHistoricoLivrosUsuario> dtoList = new ArrayList<>();

        for (Object[] obj : result) {
            EmprestimoHistoricoLivrosUsuario dto = new EmprestimoHistoricoLivrosUsuario();

            dto.setId(((BigInteger) obj[0]).longValue());
            dto.setStatus((Boolean) obj[1]);
            dto.setIdUsuario(((BigInteger) obj[2]).longValue());
            dto.setNome((String) obj[3]);
            dto.setEmail((String) obj[4]);
            dto.setIdLivro(((BigInteger) obj[5]).longValue());
            dto.setTitulo((String) obj[6]);
            dto.setCategoria((String) obj[7]);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    public List<EmprestimoHistoricoLivrosUsuario> livrosAtivosQueEstaComUsuario(Long idUser) {
        String sql =
                "select emp.id as emprestimo_id, " +
                        "       emp.status, " +
                        "       us.id as user_id, " +
                        "       us.nome as user_nome, " +
                        "       us.email, " +
                        "       liv.id as livro_id, " +
                        "       liv.titulo, " +
                        "       liv.categoria " +
                        "from emprestimo emp " +
                        "join usuario us on us.id = emp.usuario_id " +
                        "join livro liv on liv.id = emp.livro_id " +
                        "where us.id = :usuarioId " +
                        "and emp.status = true" ;

        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setParameter("usuarioId", idUser);

        List<Object[]> result = sqlQuery.list();
        List<EmprestimoHistoricoLivrosUsuario> dtoList = new ArrayList<>();

        for (Object[] obj : result) {
            EmprestimoHistoricoLivrosUsuario dto = new EmprestimoHistoricoLivrosUsuario();

            dto.setId(((BigInteger) obj[0]).longValue());
            dto.setStatus((Boolean) obj[1]);
            dto.setIdUsuario(((BigInteger) obj[2]).longValue());
            dto.setNome((String) obj[3]);
            dto.setEmail((String) obj[4]);
            dto.setIdLivro(((BigInteger) obj[5]).longValue());
            dto.setTitulo((String) obj[6]);
            dto.setCategoria((String) obj[7]);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    public List<LivroDto> livrosQuePodemSerEmprestado() {
        String sql =
                "select  \n" +
                        "    liv.id as livro_id,\n" +
                        "    liv.titulo,\n" +
                        "    liv.autor,\n" +
                        "    liv.categoria,\n" +
                        "    liv.data_publicacao \n" +
                        "from livro liv\n" +
                        "left join emprestimo emp on (liv.id = emp.livro_id and emp.status = true)\n" +
                        "where emp.id is null\n" +
                        "   or emp.status = false";

        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);

        List<Object[]> result = sqlQuery.list();
        List<LivroDto> dtoList = new ArrayList<>();

        for (Object[] obj : result) {
            LivroDto dto = new LivroDto();

            dto.setId(((BigInteger) obj[0]).longValue());
            dto.setTitulo((String) obj[1]);
            dto.setAutor((String) obj[2]);
            dto.setCategoria((String) obj[3]);
            // Converter java.sql.Date para LocalDate
            dto.setDataPublicacao(((Date) obj[4]).toLocalDate());

            dtoList.add(dto);
        }

        return dtoList;
    }

}
