package com.gestao.livros.gestaolivros.service;

import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.dto.EmprestimoHistoricoLivrosUsuario;
import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import com.gestao.livros.gestaolivros.mapper.EmprestimoMapper;
import com.gestao.livros.gestaolivros.mapper.LivroMapper;
import com.gestao.livros.gestaolivros.repository.EmprestimoRepository;
import com.gestao.livros.gestaolivros.repository.HistoricoLivrosDao;
import com.gestao.livros.gestaolivros.repository.LivroRepository;
import com.gestao.livros.gestaolivros.repository.UsuarioRepository;
import com.gestao.livros.gestaolivros.utils.ExceptionalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private EmprestimoMapper emprestimoMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    @Autowired
    private HistoricoLivrosDao historicoLivrosDao;


    public List<EmprestimoDto> getAllEmprestimo() {
        return emprestimoRepository.findAll().stream()
                .map(emprestimoMapper::toDto2)
                .collect(Collectors.toList());
    }

    //método para buscar Empréstimos pelo id
    public Optional<EmprestimoDto> findEmprestimoById(Long id) {
        return emprestimoRepository.findById(id).map(emprestimoMapper::toDto2);
    }

    //método para salvar Empréstimos
    public EmprestimoDto save(EmprestimoDto emprestimoDto) {
        boolean livroEmprestado = emprestimoRepository.existsByLivroIdAndStatusIsTrue(emprestimoDto.getIdLivro());

        if (livroEmprestado) {
            ExceptionalUtil.badRequestException("O livro está com emprestimo ativo. O mesmo so pode ser emprestado quando for devolvido e dado baixa");
        }

        EmprestimoEntity emprestimo = emprestimoMapper.convertToEntity(emprestimoDto);
        emprestimo = emprestimoRepository.save(emprestimo);
        return emprestimoMapper.toDto(emprestimo);
    }

    public Map<String,Object> saveAndSuggestBooks(EmprestimoDto emprestimoDto) {
        Map<String,Object> retorno = new HashMap<>();
        boolean livroEmprestado = emprestimoRepository.existsByLivroIdAndStatusIsTrue(emprestimoDto.getIdLivro());

        if (livroEmprestado) {
            ExceptionalUtil.badRequestException("O livro está com emprestimo ativo. O mesmo so pode ser emprestado quando for devolvido e dado baixa");
        }

        EmprestimoEntity emprestimo = emprestimoMapper.convertToEntity(emprestimoDto);
        emprestimo = emprestimoRepository.save(emprestimo);

        retorno.put("emprestimoSave",emprestimoMapper.toDto(emprestimo));
        retorno.put("suggestBooks",recomendarLivros(emprestimoDto.getIdUsuario()));

        return retorno;
    }


    //método para atualizar  Empréstimos na base
    public EmprestimoDto update(EmprestimoDto emprestimoDto) {

        if (emprestimoDto.getId() == null) {
            ExceptionalUtil.badRequestException("Informe id do empréstimo para que o mesmo possa ser alterado");
        }
        // Verifica se o Empréstimos existe na base
        if (!emprestimoRepository.existsById(emprestimoDto.getId())) {
            ExceptionalUtil.badRequestException("Empréstimo não encontrado com o ID: " + emprestimoDto.getId());
        }

        EmprestimoEntity emprestimo = emprestimoMapper.convertToEntity(emprestimoDto);
        emprestimo.setId(emprestimoDto.getId());

        // atualiza Empréstimo na base
        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDto(emprestimo);
    }

    //método para excluir um Empréstimo
    public void delete(Long id) {
        // Verifica se o Empréstimo existe na base para ser excluido
        if (!emprestimoRepository.existsById(id)) {
            ExceptionalUtil.badRequestException("Empréstimo não encontrado com o ID: " + id);
        }
        emprestimoRepository.deleteById(id);
    }

    public List<EmprestimoHistoricoLivrosUsuario> getHistoricoEmprestimo(Long idUser) {
        List<EmprestimoHistoricoLivrosUsuario> dtoList = historicoLivrosDao.historicoDeLivrosPegosPorUsuario(idUser);

        if(dtoList.isEmpty()){
            return new ArrayList<>();
        }

        return dtoList;
    }

    public List<EmprestimoHistoricoLivrosUsuario> getEmprestimoAtivoPeloUsuario(Long idUser) {
        List<EmprestimoHistoricoLivrosUsuario> dtoList = historicoLivrosDao.livrosAtivosQueEstaComUsuario(idUser);

        if(dtoList.isEmpty()){
            return new ArrayList<>();
        }

        return dtoList;
    }

    public List<LivroDto> getLivrosQuePodemSerEmprestado() {
        List<LivroDto> dtoList = historicoLivrosDao.livrosQuePodemSerEmprestado();

        if(dtoList.isEmpty()){
            return new ArrayList<>();
        }

        return dtoList;
    }

    //metodo para recomendar livros para o usuario
    public List<LivroDto> recomendarLivros(Long usuarioId) {
        //buscar livros com base nos interesses anteriores do usuárioe
        List<LivroEntity> livrosRecomendados = livroRepository.findRecomendacoesByUsuarioId(usuarioId);

        return livrosRecomendados.stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }

}
