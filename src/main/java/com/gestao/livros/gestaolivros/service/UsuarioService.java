package com.gestao.livros.gestaolivros.service;

import com.gestao.livros.gestaolivros.dto.UsuarioDto;
import com.gestao.livros.gestaolivros.entities.UsuarioEntity;
import com.gestao.livros.gestaolivros.mapper.UsuarioMapper;
import com.gestao.livros.gestaolivros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    //método para buscar todos usuários cadastrados no sistema, o mesmo retorna uma lista
    //lista todos usuarios confome a entidade e converte a entidade em um dto correspondente
    public List<UsuarioDto> getAllUser(){
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    //método para buscar usuário pelo id
    public Optional<UsuarioDto> findUserById(Long id){
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

    //método para salvar usuário
    public UsuarioDto save(UsuarioDto usuarioDto){
        UsuarioEntity usuario = usuarioMapper.toEntity(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    //método para atualizar  usuário na base
    public UsuarioDto update(UsuarioDto usuarioDto) {

        if (usuarioDto.getId() == null) {
            throw new RuntimeException("Informe id do usuario para que o mesmo possa ser alterado");
        }
        // Verifica se o usuário existe na base
        if (!usuarioRepository.existsById(usuarioDto.getId())) {
            throw new RuntimeException("Usuário não encontrado com o ID: " + usuarioDto.getId());
        }

        UsuarioEntity usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setId(usuarioDto.getId());

        // atualiza usuario na base
        usuario = usuarioRepository.save(usuario);

        return usuarioMapper.toDto(usuario);
    }

    //método para excluir um usuário
    public void delete(Long id) {
        // Verifica se o usuário existe na base para ser excluido
        if (!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

}
