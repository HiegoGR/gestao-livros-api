package com.gestao.livros.gestaolivros;


import com.gestao.livros.gestaolivros.dto.UsuarioDto;
import com.gestao.livros.gestaolivros.entities.UsuarioEntity;
import com.gestao.livros.gestaolivros.mapper.UsuarioMapper;
import com.gestao.livros.gestaolivros.repository.UsuarioRepository;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerIT extends AbstractTest{

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    private UsuarioEntity usuarioEntity;



    @BeforeEach
    public void initTest() {
        //cria usuario e salva na base para teste
        usuarioEntity = new UsuarioEntity("João","joao.silva@example.com", LocalDate.now(),"(11)98765-4321");
    }



    @Test
    @Transactional
    void getAllUsers() throws Exception {
        UsuarioEntity savedEntity = usuarioRepository.saveAndFlush(usuarioEntity);
        Long generatedId = savedEntity.getId();

        restMockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(generatedId.intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(usuarioEntity.getNome())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(usuarioEntity.getEmail())))
                .andExpect(jsonPath("$.[*].telefone").value(hasItem(usuarioEntity.getTelefone())));
    }

    @Test
    @Transactional
    void getUserById() throws Exception {
        UsuarioEntity savedEntity = usuarioRepository.saveAndFlush(usuarioEntity);
        Long generatedId = savedEntity.getId();

        restMockMvc.perform(get("/api/v1/user/{id}",generatedId.intValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(generatedId.intValue()))
                .andExpect(jsonPath("$.nome").value(usuarioEntity.getNome()))
                .andExpect(jsonPath("$.email").value(usuarioEntity.getEmail()))
                .andExpect(jsonPath("$.telefone").value(usuarioEntity.getTelefone()));
    }

    @Test
    @Transactional
    public void criarUsuario() throws Exception {
        // Verifica quantidade de usuários no banco
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Converte a entidade para DTO e define o ID como null para garantir a criação
        UsuarioDto usuarioDTO = usuarioMapper.toDto(usuarioEntity);
        usuarioDTO.setId(null);

        ResultActions resultActions = restMockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(usuarioDTO)))
                .andExpect(status().isCreated());

        // Verifica se o ID foi gerado <> null
        assertJsonWithNotNullValue(resultActions, "$.id");

        // Verifica se o número de usuários no banco de dados aumentou + 1
        assertThat(usuarioRepository.findAll().size()).isEqualTo(databaseSizeBeforeCreate + 1);

        // Verifica se o que foi retornado estão corretos
        UsuarioEntity createdUser = usuarioRepository.findAll().get(databaseSizeBeforeCreate);
        assertThat(createdUser.getNome()).isEqualTo(usuarioEntity.getNome());
        assertThat(createdUser.getEmail()).isEqualTo(usuarioEntity.getEmail());
        assertThat(createdUser.getTelefone()).isEqualTo(usuarioEntity.getTelefone());
        assertThat(createdUser.getDataCadastro()).isEqualTo(usuarioEntity.getDataCadastro());
    }

    @Test
    @Transactional
    public void atualizarUsuario() throws Exception {
        // Inicializa o banco de dados com um usuário existente
        usuarioRepository.saveAndFlush(usuarioEntity);

        // Recupera o usuário salvo e altera algumas propriedades
        UsuarioEntity updatedUsuarioEntity = usuarioRepository.findById(usuarioEntity.getId()).orElseThrow();
        updatedUsuarioEntity.setNome("Joao Atualizado");
        updatedUsuarioEntity.setTelefone("(11)91234-5678");
        updatedUsuarioEntity.setDataCadastro(LocalDate.now().minusDays(1));

        // Converte a entidade para DTO
        UsuarioDto usuarioDTO = usuarioMapper.toDto(updatedUsuarioEntity);

        ResultActions resultActions = restMockMvc.perform(put("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(usuarioDTO)))
                .andExpect(status().isOk());

        // Verifica se as mudanças foram aplicadas corretamente de acordo com o objeto esperado
        assertJson(resultActions, usuarioDTO);
    }

    @Test
    @Transactional
    public void deletarUsuario() throws Exception {
        // Inicializa o banco de dados com um usuário existente
        usuarioRepository.saveAndFlush(usuarioEntity);

        // Verifica a quantidade de usuários no banco antes da exclusão
        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        restMockMvc.perform(delete("/api/v1/user/{id}", usuarioEntity.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o número de usuários no banco de dados diminuiu em 1
        List<UsuarioEntity> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList.size()).isEqualTo(databaseSizeBeforeDelete - 1);

        // Verifica se o usuário foi realmente removido
        Optional<UsuarioEntity> deletedUsuario = usuarioRepository.findById(usuarioEntity.getId());
        assertThat(deletedUsuario.isPresent()).isFalse();
    }

}
