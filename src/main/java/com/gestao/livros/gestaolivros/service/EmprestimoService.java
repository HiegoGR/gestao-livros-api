package com.gestao.livros.gestaolivros.service;

import com.gestao.livros.gestaolivros.dto.EmprestimoDto;
import com.gestao.livros.gestaolivros.entities.EmprestimoEntity;
import com.gestao.livros.gestaolivros.entities.LivroEntity;
import com.gestao.livros.gestaolivros.entities.UsuarioEntity;
import com.gestao.livros.gestaolivros.mapper.EmprestimoMapper;
import com.gestao.livros.gestaolivros.repository.EmprestimoRepository;
import com.gestao.livros.gestaolivros.repository.LivroRepository;
import com.gestao.livros.gestaolivros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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


    public List<EmprestimoDto> getAllEmprestimo() {
        return emprestimoRepository.findAll().stream()
                .map(emprestimoMapper::toDto)
                .collect(Collectors.toList());
    }

    //método para buscar Empréstimos pelo id
    public Optional<EmprestimoDto> findEmprestimoById(Long id) {
        return emprestimoRepository.findById(id).map(emprestimoMapper::toDto);
    }

    //método para salvar Empréstimos
    public EmprestimoDto save(EmprestimoDto emprestimoDto) {
        EmprestimoEntity emprestimo = emprestimoMapper.convertToEntity(emprestimoDto);
        emprestimo = emprestimoRepository.save(emprestimo);
        return emprestimoMapper.toDto(emprestimo);
    }



    //método para atualizar  Empréstimos na base
    public EmprestimoDto update(EmprestimoDto emprestimoDto) {

        if (emprestimoDto.getId() == null) {
            throw new RuntimeException("Informe id do empréstimo para que o mesmo possa ser alterado");
        }
        // Verifica se o Empréstimos existe na base
        if (!emprestimoRepository.existsById(emprestimoDto.getId())) {
            throw new RuntimeException("Empréstimo não encontrado com o ID: " + emprestimoDto.getId());
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
            throw new RuntimeException("Empréstimo não encontrado com o ID: " + id);
        }
        emprestimoRepository.deleteById(id);
    }
}
