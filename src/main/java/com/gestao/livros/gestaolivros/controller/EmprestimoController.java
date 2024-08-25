package com.gestao.livros.gestaolivros.controller;

import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("${url.base.controller}")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/loans")
    public ResponseEntity<List<EmprestimoDto>> getAll(){
        List<EmprestimoDto> list = emprestimoService.getAllEmprestimo();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/loans/{id}")
    public ResponseEntity<Optional<EmprestimoDto>> findByIdLoan(@PathVariable Long id){
        Optional<EmprestimoDto> dto = emprestimoService.findEmprestimoById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/loans")
    public ResponseEntity<EmprestimoDto> insertLoan(@RequestBody EmprestimoDto dto){
        EmprestimoDto newDTO = emprestimoService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @PostMapping("/loans/suggest-books")
    public ResponseEntity<Map<String,Object>> insertLoanSuggestBooks(@RequestBody EmprestimoDto dto){
        Map<String,Object> saveAndSuggest = emprestimoService.saveAndSuggestBooks(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build().toUri();
        return ResponseEntity.created(uri).body(saveAndSuggest);
    }

    @GetMapping("/loans/suggest-books/user/{id}")
    public ResponseEntity<List<LivroDto>> findSuggestBooksForUser(@PathVariable("id") Long idUser){
        List<LivroDto> dtoList = emprestimoService.recomendarLivros(idUser);
        return ResponseEntity.ok().body(dtoList);
    }

    @PutMapping("/loans")
    public ResponseEntity<EmprestimoDto> updateLoan(@RequestBody EmprestimoDto dto){
        EmprestimoDto newDto = emprestimoService.update(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id){
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
