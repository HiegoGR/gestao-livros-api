package com.gestao.livros.gestaolivros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String nome;

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @PastOrPresent(message = "A data de cadastro não pode ser no futuro.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;

    @NotNull
    @Size(max = 14)
    private String telefone;
}
