package com.gestao.livros.gestaolivros.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "livro")
@Data
public class LivroDto {

    @Id
    private Long Id;

    @NotNull
    @Size(max = 100)
    private String titulo;

    @NotNull
    @Size(max = 50)
    private String autor;

    @NotNull
    @Size(max = 50)
    private String categoria;

    @NotNull
    private LocalDate dataPublicacao;

}
