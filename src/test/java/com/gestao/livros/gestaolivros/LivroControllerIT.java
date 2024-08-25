package com.gestao.livros.gestaolivros;


import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import com.gestao.livros.gestaolivros.mapper.LivroMapper;
import com.gestao.livros.gestaolivros.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerIT extends AbstractTest{

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;


    private LivroEntity livroEntity;

    @BeforeEach
    public void initTest() {
        //cria livro e salva na base para teste
        livroEntity = new LivroEntity("The Great Gatsby","F. Scott Fitzgerald","Classic",LocalDate.now());
    }



    @Test
    @Transactional
    void getAllBooks() throws Exception {
        LivroEntity savedEntity = livroRepository.saveAndFlush(livroEntity);
        Long generatedId = savedEntity.getId();

        restMockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(generatedId.intValue())))
                .andExpect(jsonPath("$.[*].titulo").value(hasItem(livroEntity.getTitulo())))
                .andExpect(jsonPath("$.[*].autor").value(hasItem(livroEntity.getAutor())))
                .andExpect(jsonPath("$.[*].categoria").value(hasItem(livroEntity.getCategoria())))
                .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(convertDateToString(livroEntity.getDataPublicacao()))));

    }

    @Test
    @Transactional
    void getBookById() throws Exception {
        LivroEntity savedEntity = livroRepository.saveAndFlush(livroEntity);
        Long generatedId = savedEntity.getId();

        restMockMvc.perform(get("/api/v1/books/{id}",generatedId.intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(generatedId.intValue()))
                .andExpect(jsonPath("$.titulo").value(livroEntity.getTitulo()))
                .andExpect(jsonPath("$.autor").value(livroEntity.getAutor()))
                .andExpect(jsonPath("$.categoria").value(livroEntity.getCategoria()))
                .andExpect(jsonPath("$.dataPublicacao").value(convertDateToString(livroEntity.getDataPublicacao())));
    }

    @Test
    @Transactional
    public void criarLivro() throws Exception {
        // Verifica quantidade de livors no banco
        int databaseSizeBeforeCreate = livroRepository.findAll().size();

        // Converte a entidade para DTO e define o ID como null para garantir a criação
        LivroDto livroDto = livroMapper.toDto(livroEntity);
        livroDto.setId(null);

        ResultActions resultActions = restMockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(livroDto)))
                .andExpect(status().isCreated());

        // Verifica se o ID foi gerado <> null
        assertJsonWithNotNullValue(resultActions, "$.id");

        // Verifica se o número de livros no banco de dados aumentou + 1
        assertThat(livroRepository.findAll().size()).isEqualTo(databaseSizeBeforeCreate + 1);

        // Verifica se o que foi retornado estão corretos
        LivroEntity createdBook = livroRepository.findAll().get(databaseSizeBeforeCreate);

        assertThat(createdBook.getTitulo()).isEqualTo(livroEntity.getTitulo());
        assertThat(createdBook.getAutor()).isEqualTo(livroEntity.getAutor());
        assertThat(createdBook.getCategoria()).isEqualTo(livroEntity.getCategoria());
        assertThat(createdBook.getDataPublicacao()).isEqualTo(livroEntity.getDataPublicacao());
    }

    @Test
    @Transactional
    public void atualizarLivro() throws Exception {
        // Inicializa o banco de dados com um livro existente
        livroRepository.saveAndFlush(livroEntity);

        // Recupera o livro salvo e altera algumas propriedades
        LivroEntity updatedLivroEntity = livroRepository.findById(livroEntity.getId()).orElseThrow();
        updatedLivroEntity.setTitulo("Romeo and Juliet");
        updatedLivroEntity.setAutor("William Shakespeare");
        updatedLivroEntity.setCategoria("Tragedy");
        updatedLivroEntity.setDataPublicacao(LocalDate.parse("1597-01-01"));

        // Converte a entidade para DTO
        LivroDto livroDto = livroMapper.toDto(updatedLivroEntity);

        ResultActions resultActions = restMockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(livroDto)))
                .andExpect(status().isOk());

        // Verifica se as mudanças foram aplicadas corretamente de acordo com o objeto esperado
        assertJson(resultActions, livroDto);
    }

    @Test
    @Transactional
    public void deletarLivro() throws Exception {
        // Inicializa o banco de dados com um livro existente
        livroRepository.saveAndFlush(livroEntity);

        // Verifica a quantidade de livro no banco antes da exclusão
        int databaseSizeBeforeDelete = livroRepository.findAll().size();

        restMockMvc.perform(delete("/api/v1/books/{id}", livroEntity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o número de livros no banco de dados diminuiu em 1
        List<LivroEntity> livroList = livroRepository.findAll();
        assertThat(livroList.size()).isEqualTo(databaseSizeBeforeDelete - 1);

        // Verifica se o livro foi realmente removido
        Optional<LivroEntity> deletedLivro = livroRepository.findById(livroEntity.getId());
        assertThat(deletedLivro.isPresent()).isFalse();
    }

}
