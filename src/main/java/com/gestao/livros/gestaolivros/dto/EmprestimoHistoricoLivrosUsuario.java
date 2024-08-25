package com.gestao.livros.gestaolivros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoHistoricoLivrosUsuario {

    private Long id;

    private Long idUsuario;

    private String nome;

    private String email;

    private Long idLivro;

    private String titulo;

    private String categoria;

    private Boolean status;
}
