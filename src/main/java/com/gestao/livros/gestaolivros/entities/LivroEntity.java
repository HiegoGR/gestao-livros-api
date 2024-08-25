package com.gestao.livros.gestaolivros.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "livro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(max = 50)
    @Column(name = "autor", length = 50, nullable = false)
    private String autor;

    @NotNull
    @Size(max = 50)
    @Column(name = "categoria", length = 50, nullable = false)
    private String categoria;

    @NotNull
    @Column(name = "data_publicacao", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataPublicacao;

    public LivroEntity(Long id) {
        this.id = id;
    }

    public LivroEntity(String titulo, String autor, String categoria, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }
}
