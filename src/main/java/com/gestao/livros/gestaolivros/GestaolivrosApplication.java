package com.gestao.livros.gestaolivros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GestaolivrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaolivrosApplication.class, args);
	}

}
