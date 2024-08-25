# Sistema de Gestão de Livros

Este é um sistema de gestão de livros desenvolvido com Java 11 e Spring Boot. 
O sistema inclui funcionalidades para gerenciar empréstimos de livros, usuários e livros. 
Com intuito de desenvolver API REST para as operações CRUD é fazer a integração com a API do Google Books

## Feito com
* [Java 11](#Título-e-Imagem-de-capa)
* [SpringBoot](#badges)
* [H2 Database](#badges)

## Build and Run

1. Clone o repositório

   ``` git clone https://github.com/HiegoGR/gestao-livros-api.git```

2. Depois que realizar o clone, na sua IDE execute os comandos

   ``` mvn clean package ```


## Documentação da API
* Para visualizar a documentação da API e testar os endpoints, acesse o Swagger:
* Ao executar o projeto, vá ate o navegador é digite:
  ```
  http://localhost:8090/swagger-ui/index.html    
* assim poderá observar os endPoints em que o projeto está usando é exportar para o POSTMAN.


## Obs

1. A configuração do H2 Database está definida automaticamente e não requer configuração manual. 
   O banco de dados será inicializado em memória e será populado com dados de exemplo na inicialização 
   da aplicação.
2. A aplicação possui um tratamento global de erros. Caso ocorra um erro, 
   o formato da resposta será similar a está:
     ``` {
   "timestamp": 1724610087,
   "status": 400,
   "error": "Bad Request",
   "message": "Empréstimo de livro com data futura não é permitido.",
   "path": "/api/v1/loans"
   }