package com.gestao.livros.gestaolivros.controller;

import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.service.LivroService;
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
public class LivroController {

    @Autowired
    private LivroService livroService;

    @ApiOperation(value = "Lista todos os 'livros'", notes = "Retorna uma lista com todos os 'livros'")
    @GetMapping("/books")
    public ResponseEntity<List<LivroDto>> getAllBooks(){
        List<LivroDto> list = livroService.getAllLivro();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Busca 'livro' pelo ID", notes = "Retorna 'livro' pesquisado pelo ID fornecido")
    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<LivroDto>> findByIdBook(@PathVariable Long id){
        Optional<LivroDto> dto = livroService.findLivroById(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Retorna uma lista de livros pesquisados pela API do google", notes = "Retorna lista de livros pesquisados com base no nome fornecido, utilizando a API do google book")
    @GetMapping("/books/google-api/name/{name}")
    public ResponseEntity<List<LivroDto>> findBookName(@PathVariable("name") String bookName){
        return ResponseEntity.ok().body(livroService.findBookName(bookName));
    }

    @ApiOperation(value = "Insere um novo 'livro' ", notes = "Cadastra um 'livro'")
    @PostMapping("/books")
    public ResponseEntity<LivroDto> insertBook(@RequestBody LivroDto dto){
        LivroDto newDTO = livroService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @ApiOperation(value = "Atualiza dados do 'livro' ", notes = "Atualiza dados do 'livro'")
    @PutMapping("/books")
    public ResponseEntity<LivroDto> updateBook(@RequestBody LivroDto dto){
        LivroDto newDto = livroService.update(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @ApiOperation(value = "Deleta um 'livro' ", notes = "Deleta um 'livro' pelo ID fornecido")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
