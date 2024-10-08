package com.gestao.livros.gestaolivros.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    @Column(name = "nome", length = 30, nullable = false)
    private String nome;

    @NotNull
    @Email(message = "O e-mail não esta no formato correto, favor conferir.")
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @PastOrPresent(message = "A data de cadastro não pode ser no futuro.")
    @Column(name = "data_cadastro", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;

    @NotNull
    @Size(max = 14)
    @Column(name = "telefone", length = 14, nullable = false)
    private String telefone;

    public UsuarioEntity(Long id) {
        this.id = id;
    }

    public UsuarioEntity(String nome, String email, LocalDate dataCadastro, String telefone) {
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.telefone = telefone;
    }
}
