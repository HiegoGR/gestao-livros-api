package com.gestao.livros.gestaolivros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataPublicacao;

    public LivroDto(String titulo, String autor, String categoria, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }
}
