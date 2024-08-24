package com.gestao.livros.gestaolivros.repository;

import com.gestao.livros.gestaolivros.entities.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity,Long> {

}
