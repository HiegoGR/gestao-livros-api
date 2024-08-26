package com.gestao.livros.gestaolivros.controller;

import com.gestao.livros.gestaolivros.dto.UsuarioDto;
import com.gestao.livros.gestaolivros.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${url.base.controller}")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Lista todos os 'usuários'", notes = "Retorna uma lista com todos os 'usuários'")
    @GetMapping("/user")
    public ResponseEntity<List<UsuarioDto>> getAll(){
        List<UsuarioDto> list = usuarioService.getAllUser();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Busca 'usuário' pelo ID", notes = "Retorna 'usuário' pesquisado pelo ID fornecido")
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UsuarioDto>> findByIdUser(@PathVariable Long id){
        Optional<UsuarioDto> dto = usuarioService.findUserById(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Insere um novo 'usuário' ", notes = "Cadastra um 'usuário'")
    @PostMapping("/user")
    public ResponseEntity<UsuarioDto> insertUser(@RequestBody UsuarioDto dto){
        UsuarioDto newDTO = usuarioService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @ApiOperation(value = "Atualiza dados do 'usuário' ", notes = "Atualiza dados do 'usuário'")
    @PutMapping("/user")
    public ResponseEntity<UsuarioDto> updateUser(@RequestBody UsuarioDto dto){
        UsuarioDto newDto = usuarioService.update(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @ApiOperation(value = "Deleta um 'usuário' ", notes = "Deleta um 'usuário' pelo ID fornecido")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
