package com.gestao.livros.gestaolivros.entities;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
@Data
public class EmprestimoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Column(name = "usuario_id", length = 100, nullable = false)
    private Long idUsuario;

    @NotNull
    @Column(name = "livro_id", length = 100, nullable = false)
    private Long idLivro;

    @NotNull
    @PastOrPresent(message = "Empréstimo de livro com data futura não é permitido.")
    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo;

    @NotNull
    @Column(name = "data_devolucao", nullable = false)
    private LocalDate dataDevolucao;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;
}
