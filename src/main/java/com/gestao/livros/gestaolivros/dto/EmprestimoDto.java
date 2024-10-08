package com.gestao.livros.gestaolivros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idLivro;

    @NotNull
    @PastOrPresent(message = "Empréstimo de livro com data futura não é permitido.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataEmprestimo;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucao;

    @NotNull
    private Boolean status;

}
