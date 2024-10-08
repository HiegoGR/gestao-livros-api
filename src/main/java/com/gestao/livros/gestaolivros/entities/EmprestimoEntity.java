package com.gestao.livros.gestaolivros.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "emprestimo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // Define o relacionamento um para muitos
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER) // Define o relacionamento um para muitos
    @JoinColumn(name = "livro_id", nullable = false)
    private LivroEntity livro;

    @NotNull
    @PastOrPresent(message = "Empréstimo de livro com data futura não é permitido.")
    @Column(name = "data_emprestimo", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataEmprestimo;

    @NotNull
    @Column(name = "data_devolucao", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucao;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;
}
