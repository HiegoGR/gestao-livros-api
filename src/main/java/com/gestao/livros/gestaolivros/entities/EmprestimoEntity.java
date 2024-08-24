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
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // Define o relacionamento um para muitos
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // Define o relacionamento um para muitos
    @JoinColumn(name = "livro_id", nullable = false)
    private LivroEntity livro;

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
