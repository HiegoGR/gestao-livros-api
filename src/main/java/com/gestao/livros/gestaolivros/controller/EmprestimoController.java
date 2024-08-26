package com.gestao.livros.gestaolivros.controller;

import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.dto.EmprestimoHistoricoLivrosUsuario;
import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.service.EmprestimoService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Lista todos os 'empréstimos'", notes = "Retorna uma lista com todos os 'empréstimos'")
    @GetMapping("/loans")
    public ResponseEntity<List<EmprestimoDto>> getAll(){
        List<EmprestimoDto> list = emprestimoService.getAllEmprestimo();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Busca 'empréstimo' pelo ID", notes = "Retorna 'empréstimos' pesquisado pelo ID fornecido")
    @GetMapping("/loans/{id}")
    public ResponseEntity<Optional<EmprestimoDto>> findByIdLoan(@PathVariable Long id){
        Optional<EmprestimoDto> dto = emprestimoService.findEmprestimoById(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Insere um 'empréstimo' ", notes = "Cria um 'empréstimo'")
    @PostMapping("/loans")
    public ResponseEntity<EmprestimoDto> insertLoan(@RequestBody EmprestimoDto dto){
        EmprestimoDto newDTO = emprestimoService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(newDTO);
    }

    @ApiOperation(value = "Insere um 'empréstimo' e retorna livros sugeridos para o usário", notes = "Cria um 'empréstimo' e retorna livros sugeridos para o usário")
    @PostMapping("/loans/suggest-books")
    public ResponseEntity<Map<String,Object>> insertLoanSuggestBooks(@RequestBody EmprestimoDto dto){
        Map<String,Object> saveAndSuggest = emprestimoService.saveAndSuggestBooks(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build().toUri();
        return ResponseEntity.created(uri).body(saveAndSuggest);
    }

    @ApiOperation(value = "Atualiza dados do 'empréstimo' ", notes = "EndPoint atualiza dados do 'empréstimo'")
    @PutMapping("/loans")
    public ResponseEntity<EmprestimoDto> updateLoan(@RequestBody EmprestimoDto dto){
        EmprestimoDto newDto = emprestimoService.update(dto);
        return ResponseEntity.ok().body(newDto);
    }

    @ApiOperation(value = "Lista livros sugeridosdo para usário", notes = "Retorna uma lista de livros sugeridosdo para ID do usário fornecido")
    @GetMapping("/loans/suggest-books/user/{id}")
    public ResponseEntity<List<LivroDto>> findSuggestBooksForUser(@PathVariable("id") Long idUser){
        List<LivroDto> dtoList = emprestimoService.recomendarLivros(idUser);
        return ResponseEntity.ok().body(dtoList);
    }

    @ApiOperation(value = "Deleta um 'empréstimo' ", notes = "Deleta um 'empréstimo' pelo ID fornecido")
    @DeleteMapping("/loans/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id){
        emprestimoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Lista histórico de livros para usuário", notes = "Retorna uma lista com todos os livros que o usário pegou emprestado, pelo ID do usuário fornecido")
    @GetMapping("/loans/books/history/user/{id}")
    public ResponseEntity<List<EmprestimoHistoricoLivrosUsuario>> findHistoryBooksForUser(@PathVariable("id") Long idUser){
        List<EmprestimoHistoricoLivrosUsuario> dtoList = emprestimoService.getHistoricoEmprestimo(idUser);
        return ResponseEntity.ok().body(dtoList);
    }

    @ApiOperation(value = "Lista livros ativos com usuário", notes = "Retorna uma lista com todos os livros ativos que não foram devolvidos pelo ID fornecido do usuário")
    @GetMapping("/loans/books-active/user/{id}")
    public ResponseEntity<List<EmprestimoHistoricoLivrosUsuario>> findBooksActiveForUser(@PathVariable("id") Long idUser){
        List<EmprestimoHistoricoLivrosUsuario> dtoList = emprestimoService.getEmprestimoAtivoPeloUsuario(idUser);
        return ResponseEntity.ok().body(dtoList);
    }

    @ApiOperation(value = "Lista livros que podem ser emprestados", notes = "Retorna uma lista com todos os livros que podem ser emprestado para outros usuarios")
    @GetMapping("/loans/books-can-borred")
    public ResponseEntity<List<LivroDto>> getAllBooksCanForLoans(){
        List<LivroDto> dtoList = emprestimoService.getLivrosQuePodemSerEmprestado();
        return ResponseEntity.ok().body(dtoList);
    }






}
