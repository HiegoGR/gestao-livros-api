package com.gestao.livros.gestaolivros.service;

import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import com.gestao.livros.gestaolivros.mapper.LivroMapper;
import com.gestao.livros.gestaolivros.repository.LivroRepository;
import com.gestao.livros.gestaolivros.utils.ExceptionalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    @Autowired
    private GoogleBooksService googleBooksService;


    public List<LivroDto> getAllLivro() {
        return livroRepository.findAll().stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }

    //método para buscar Livro pelo id
    public Optional<LivroDto> findLivroById(Long id) {
        return livroRepository.findById(id).map(livroMapper::toDto);
    }

    //método para salvar Livro
    public LivroDto save(LivroDto livroDto) {
        LivroEntity livro = livroMapper.toEntity(livroDto);
        livro = livroRepository.save(livro);
        return livroMapper.toDto(livro);
    }

    //método para atualizar Livro na base
    public LivroDto update(LivroDto livroDto) {

        if (livroDto.getId() == null) {
            ExceptionalUtil.badRequestException("Informe id do livro para que o mesmo possa ser alterado");
        }
        // Verifica se o Livro existe na base
        if (!livroRepository.existsById(livroDto.getId())) {
            ExceptionalUtil.badRequestException("Livro não encontrado com o ID: " + livroDto.getId());
        }

        LivroEntity livro = livroMapper.toEntity(livroDto);
        livro.setId(livroDto.getId());

        // atualiza Livro na base
        livro = livroRepository.save(livro);

        return livroMapper.toDto(livro);
    }

    //método para excluir um Livro
    public void delete(Long id) {
        // Verifica se o Livro existe na base para ser excluido
        if (!livroRepository.existsById(id)) {
            ExceptionalUtil.badRequestException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    public List<LivroDto> findBookName(String bookName) {
        List<LivroDto> bookList = googleBooksService.getListGoogleBooksSearchReturnLivroDto(bookName);

        if (bookList == null || bookList.isEmpty()) {
            return new ArrayList<>();
        }

        //limitando o retorno da lista para 3 elementos
        return bookList.subList(0, 3);
    }

}
