package com.gestao.livros.gestaolivros;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(classes = SpringExtension.class)
@WebAppConfiguration
public abstract class AbstractTest {
    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(json, clazz);
    }

    // Método auxiliar para verificar se o valor no JSON não é nulo
    protected void assertJsonWithNotNullValue(ResultActions resultActions, String jsonPath) throws Exception {
        resultActions.andExpect(jsonPath(jsonPath).value(notNullValue()));
    }

    public static void assertJson(ResultActions resultActions, Object expectedObject) throws Exception {
        // Converte o objeto esperado para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Converte o objeto esperado para JSON
        String expectedJson = objectMapper.writeValueAsString(expectedObject);

        // Obtém o JSON retornado pelo ResultActions
        String actualJson = resultActions.andReturn().getResponse().getContentAsString();

        // Compara os dois JSONs
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

    // Método que converte LocalDate para o formato "yyyy-MM-dd"
    public static String convertDateToString(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }

        // Formata a data no formato "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

}
