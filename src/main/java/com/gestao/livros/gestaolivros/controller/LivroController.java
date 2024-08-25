package com.gestao.livros.gestaolivros.controller;

import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.service.LivroService;
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

    @GetMapping("/books")
    public ResponseEntity<List<LivroDto>> getAllBooks(){
        List<LivroDto> list = livroService.getAllLivro();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<LivroDto>> findByIdBook(@PathVariable Long id){
        Optional<LivroDto> dto = livroService.findLivroById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/books/google-api/name/{name}")
    public ResponseEntity<List<LivroDto>> findBookName(@PathVariable("name") String bookName){
        return ResponseEntity.ok().body(livroService.findBookName(bookName));
    }

    @PostMapping("/books")
    public ResponseEntity<LivroDto> insertBook(@RequestBody LivroDto dto){
        LivroDto newDTO = livroService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PutMapping("/books")
    public ResponseEntity<LivroDto> updateBook(@RequestBody LivroDto dto){
        LivroDto newDto = livroService.update(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
