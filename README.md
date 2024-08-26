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

1. A modelagem do banco de dados está sendo gerada automaticamente pelo Hibernate.
   Usando as anotaçoes do Spring Boot para gerenciar o mapeamento entre as classes Java e as tabelas do banco de dados.

Logo a modelagem do banco ficaria desta maneira :
```
create table usuario (
id bigint auto_increment primary key,
nome varchar(30) not null,
email varchar(50) not null,
data_cadastro date not null check (data_cadastro <= current_date),
telefone varchar(14) not null
);

create table livro (
id bigint auto_increment primary key,
titulo varchar(100) not null,
autor varchar(50) not null,
isbn varchar(50) not null unique,
data_publicacao date not null,
categoria varchar(50) not null
);

create table emprestimo (
id bigserial auto_increment primary key,
usuario_id bigint not null,
livro_id bigint not null,
data_emprestimo date not null check (data_emprestimo <= current_date),
data_devolucao date not null,
status boolean not null,
foreign key (usuario_id) references usuario(id),
foreign key (livro_id) references livro(id),
constraint uc_livro_emprestimo_ativo unique (livro_id, status)
);
```

3. A configuração do banco H2 está definida automaticamente e não precisa de configuração manual. 
   O banco de dados será inicializado em memória e será populado com dados de exemplo na inicialização 
   da aplicação.
4. A aplicação possui um tratamento global de erros. Caso ocorra um erro, 
   o formato da resposta será similar a está:
     ``` {
   "timestamp": 1724610087,
   "status": 400,
   "error": "Bad Request",
   "message": "Empréstimo de livro com data futura não é permitido.",
   "path": "/api/v1/loans"
   }