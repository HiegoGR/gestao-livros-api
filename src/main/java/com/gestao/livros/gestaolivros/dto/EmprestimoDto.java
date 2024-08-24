package com.gestao.livros.gestaolivros.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@Data
public class EmprestimoDto {

    @Id
    private Long Id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idLivro;

    @NotNull
    @PastOrPresent(message = "Empréstimo de livro com data futura não é permitido.")
    private LocalDate dataEmprestimo;

    @NotNull
    private LocalDate dataDevolucao;

    @NotNull
    private Boolean status;
}
