package com.example.pessoasapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.pessoasapi.repository.PessoaRepository;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PessoaControllerTest {

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new PessoaController(new PessoaRepository()))
            .setValidator(validator())
            .build();

    private static LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return validator;
    }

    @Test
    void deveCadastrarPessoa() throws Exception {
        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Maria Silva",
                                  "email": "maria@example.com",
                                  "senha": "senha1234"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome").value("Maria Silva"))
                .andExpect(jsonPath("$.email").value("maria@example.com"))
                .andExpect(jsonPath("$.senha").doesNotExist());
    }

    @Test
    void deveBloquearEmailDuplicado() throws Exception {
        String body = """
                {
                  "nome": "Joao Silva",
                  "email": "joao@example.com",
                  "senha": "senha1234"
                }
                """;

        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void deveValidarDadosObrigatorios() throws Exception {
        mockMvc.perform(post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "",
                                  "email": "email-invalido",
                                  "senha": "curta"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
