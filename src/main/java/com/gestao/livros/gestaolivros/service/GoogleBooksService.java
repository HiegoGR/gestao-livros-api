package com.gestao.livros.gestaolivros.service;

import com.gestao.livros.gestaolivros.dto.GoogleBooksResponseDto;
import com.gestao.livros.gestaolivros.dto.LivroDto;
import com.gestao.livros.gestaolivros.utils.ConsumirApiUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gestao.livros.gestaolivros.utils.DateUtils.localDateAjustes;

@Service
@Transactional
public class GoogleBooksService {

    @Value("${api.url.google.book}")
    private String url;

    @Value("${key.teste}")
    private String key;


    //método para consumir api do google passando nome do livro retornando uma lista com livros compatíveis com o nome pesquisado
    public GoogleBooksResponseDto getListGoogleBooksSearch(String bookName){
        //consumo APi do google passndo o nome do livro em que quero pesquisar e o mesmo retornar uma lista
        ResponseEntity<GoogleBooksResponseDto> responseEntity = ConsumirApiUtil
                .metodoObterListaDadosPorGetComQueryParams(
                        url,
                        bookName,
                        key,
                        GoogleBooksResponseDto.class
                );

        //Verifica se objeto retornado nao é nulo nem vazio
        GoogleBooksResponseDto responseBody = responseEntity.getBody();

        if (responseBody != null && responseBody.getItems() != null) {
            return responseBody;
        }else{
            return null;
        }

    }

    //método para consumir api do google passando nome do livro retornando uma lista de LivroDto;
    public List<LivroDto> getListGoogleBooksSearchReturnLivroDto(String bookName){
        List<LivroDto> livroDtoList = new ArrayList<>();

        ResponseEntity<GoogleBooksResponseDto> responseEntity = ConsumirApiUtil
                .metodoObterListaDadosPorGetComQueryParams(
                        url,
                        bookName,
                        key,
                        GoogleBooksResponseDto.class
                );

        GoogleBooksResponseDto responseBody = responseEntity.getBody();
        if (responseBody != null && responseBody.getItems() != null) {

            for(GoogleBooksResponseDto.ItemDto item : responseEntity.getBody().getItems()){
                if(item.getVolumeInfo() != null){
                    LivroDto livroDto =
                            new LivroDto(item.getVolumeInfo().getTitle(),
                                         item.getVolumeInfo().getAuthors() != null && !item.getVolumeInfo().getAuthors().isEmpty() ? item.getVolumeInfo().getAuthors().get(0) : "Autor Desconhecido",
                                         item.getVolumeInfo().getCategories() != null && !item.getVolumeInfo().getCategories().isEmpty() ? item.getVolumeInfo().getCategories().get(0).getIdentifierCategory() : "Não Identificado",// pegar primeira categoria caso estaja em mais de uma
                                         item.getVolumeInfo().getPublishedDate() != null ? localDateAjustes(item.getVolumeInfo().getPublishedDate()) : LocalDate.now()//caso nao ter uma data pega data atual caso contrario pega data que foi informada
                            );

                    livroDtoList.add(livroDto);
                }

            }
            return livroDtoList;
        }else{
            return new ArrayList<>();
        }

    }

}
