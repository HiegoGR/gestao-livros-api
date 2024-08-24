package com.gestao.livros.gestaolivros.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioDto {

    @Id
    private Long Id;

    @NotNull
    @Size(max = 30)
    private String nome;

    @Email
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    private LocalDate dataCadastro;

    @NotNull
    @Size(max = 14)
    private String telefone;
}
