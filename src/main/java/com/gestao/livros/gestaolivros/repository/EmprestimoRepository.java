package com.gestao.livros.gestaolivros.repository;

import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity,Long> {

}
