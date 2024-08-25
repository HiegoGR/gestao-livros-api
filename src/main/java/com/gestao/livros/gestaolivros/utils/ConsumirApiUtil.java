package com.gestao.livros.gestaolivros.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ConsumirApiUtil {

    private static final Logger log = LogManager.getLogger(ConsumirApiUtil.class);


    public static void metodoParaSalvar(String url, String token, Object dto) {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar o cabeçalho da requisição com o token de autorização
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        // Realizar a requisição POST
        try {
            ResponseEntity<String> response = restTemplate
                    .exchange(
                            url,
                            HttpMethod.POST,
                            new HttpEntity<>(dto, headers),
                            String.class
                    );

            if (!response.getStatusCode().is2xxSuccessful()) {
                ExceptionalUtil.badRequestException("falhachamarapi"+response.getBody());
            }
        } catch (HttpClientErrorException e) {
            ExceptionalUtil.badRequestException("falhachamarapi"+extractErrorMessage(e.getResponseBodyAsString()));
        }
    }

 public static ResponseEntity<?> metodoParaAtualizar(String url, String token, Object dto) {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar o cabeçalho da requisição com o token de autorização
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        // Realizar a requisição PUT
        try {
            ResponseEntity<String> response = restTemplate
                    .exchange(
                            url,
                            HttpMethod.PUT,
                            new HttpEntity<>(dto, headers),
                            String.class
                    );

            // Verificar o código de status da resposta
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else if (response.getStatusCode().is4xxClientError()) {
                // Tratar erro do cliente (4xx)
                ExceptionalUtil.badRequestException("falhachamarapi"+response.getStatusCode().getReasonPhrase());
            } else if (response.getStatusCode().is5xxServerError()) {
                // Tratar erro do servidor (5xx)
                ExceptionalUtil.badRequestException("falhachamarapi"+response.getStatusCode().getReasonPhrase());
            }

        } catch (HttpClientErrorException e) {
            // Tratar exceção específica do cliente HTTP
            ExceptionalUtil.badRequestException("falhachamarapi"+ extractErrorMessage(e.getResponseBodyAsString()));
//            ExceptionalUtil.badRequestException(extractErrorMessage(e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            // Tratar exceção específica do servidor HTTP
            ExceptionalUtil.internalServerErroException("erroprocessarrequisicao"+ extractErrorMessage(e.getResponseBodyAsString()));
        } catch (Exception e) {
            // Tratar outras exceções
            ExceptionalUtil.badRequestException("erroprocessarrequisicao"+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorno padrão em caso de erro não tratado
    }


    public static <T> ResponseEntity<T> metodoParaObterDadosPorGetSemPaginacao(String baseUrl, String token,
                                                                               Class<T> retornoRequisicao) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho da requisicao com o token de autorizacao
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        try {
            //configura o retorno do response requisicao
            ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {
            };

            // Realizar a requisição POST
            ResponseEntity<T> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    responseType
            );

            // Verificar o código de status da resposta
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else if (response.getStatusCode().is4xxClientError()) {
                // Tratar erro do cliente (4xx)
                ExceptionalUtil.badRequestException("Falha ao chamar a API:"+response.getStatusCode().getReasonPhrase());
            } else if (response.getStatusCode().is5xxServerError()) {
                // Tratar erro do servidor (5xx)
                ExceptionalUtil.internalServerErroException("Erro de cliente: "+response.getStatusCode().getReasonPhrase());
            }

        } catch (HttpClientErrorException e) {
            // Tratar exceção específica do cliente HTTP
            ExceptionalUtil.badRequestException("Falha ao chamar Api:"+ extractErrorMessage(e.getResponseBodyAsString()));
//            ExceptionalUtil.badRequestException(extractErrorMessage(e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            // Tratar exceção específica do servidor HTTP
            ExceptionalUtil.internalServerErroException("Erro de servidor: "+ extractErrorMessage(e.getResponseBodyAsString()));
        } catch (Exception e) {
            // Tratar outras exceções
            ExceptionalUtil.internalServerErroException("Erro inesperado: "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorno padrão em caso de erro não tratado
    }

    public static <T> ResponseEntity<List<T>> metodoParaObterListaGetSemPaginacao(String baseUrl, String token,
                                                                                          Class<T> retornoRequisicao) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho da requisicao com o token de autorizacao
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        try {
            // Configura o retorno do response requisicao
            ParameterizedTypeReference<List<T>> responseType = new ParameterizedTypeReference<List<T>>() {
            };

            // Realizar a requisição GET
            ResponseEntity<List<T>> response = restTemplate.exchange(
                    baseUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    responseType
            );

            // Verificar o código de status da resposta
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else if (response.getStatusCode().is4xxClientError()) {
                // Tratar erro do cliente (4xx)
                ExceptionalUtil.badRequestException("Falha ao chamar a API:"+response.getStatusCode().getReasonPhrase());
            } else if (response.getStatusCode().is5xxServerError()) {
                // Tratar erro do servidor (5xx)
                ExceptionalUtil.internalServerErroException("Erro de cliente: "+response.getStatusCode().getReasonPhrase());
            }

        } catch (HttpClientErrorException e) {
            // Tratar exceção específica do cliente HTTP
            ExceptionalUtil.badRequestException("Falha ao chamar Api:"+ extractErrorMessage(e.getResponseBodyAsString()));
//            ExceptionalUtil.badRequestException(extractErrorMessage(e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            // Tratar exceção específica do servidor HTTP
            ExceptionalUtil.internalServerErroException("Erro de servidor: "+ extractErrorMessage(e.getResponseBodyAsString()));
        } catch (Exception e) {
            // Tratar outras exceções
            ExceptionalUtil.internalServerErroException("Erro inesperado: "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorno padrão em caso de erro não tratado
    }


    public static <T> ResponseEntity<T> metodoObterListaDadosPorGetComQueryParams(String baseUrl, String searchBook, String key,
                                                                                  Class<T> retornoRequisicao) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho da requisicao com o token de autorizacao
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Colocando Query Params na URL
        String url = baseUrl + "?q=" + searchBook + "&key=" + key;

        try {
            // Realizar a requisição GET
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    retornoRequisicao
            );

            // Verificar o código de status da resposta
            if (response.getStatusCode().is2xxSuccessful()) {
                return response;
            } else if (response.getStatusCode().is4xxClientError()) {
                // Tratar erro do cliente (4xx)
                ExceptionalUtil.badRequestException("Falha ao chamar a API:"+response.getStatusCode().getReasonPhrase());
            } else if (response.getStatusCode().is5xxServerError()) {
                // Tratar erro do servidor (5xx)
                ExceptionalUtil.internalServerErroException("Erro de cliente: "+response.getStatusCode().getReasonPhrase());
            }

        } catch (HttpClientErrorException e) {
            // Tratar exceção específica do cliente HTTP
            ExceptionalUtil.badRequestException("Falha ao chamar Api:"+ extractErrorMessage(e.getResponseBodyAsString()));
//            ExceptionalUtil.badRequestException(extractErrorMessage(e.getResponseBodyAsString()));
        } catch (HttpServerErrorException e) {
            // Tratar exceção específica do servidor HTTP
            ExceptionalUtil.internalServerErroException("Erro de servidor: "+ extractErrorMessage(e.getResponseBodyAsString()));
        } catch (Exception e) {
            // Tratar outras exceções
            ExceptionalUtil.internalServerErroException("Erro inesperado: "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorno padrão em caso de erro não tratado
    }



    //Metodo para extrair mensagem de erro que foi gerada ao consumir uma api(msg de um 4xx)
    private static String extractErrorMessage(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode messageNode = rootNode.path("message");

            if (!messageNode.isMissingNode()) {
                return messageNode.asText();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return responseBody;
    }


}
