package com.gestao.livros.gestaolivros.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.Size;
import java.time.LocalDate;


public class LivroDto {

    private Long id;

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
