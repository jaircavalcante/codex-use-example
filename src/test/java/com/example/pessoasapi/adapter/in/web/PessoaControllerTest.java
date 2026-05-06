package com.example.pessoasapi.adapter.in.web;

import com.example.pessoasapi.adapter.out.persistence.PessoaEmMemoriaRepository;
import com.example.pessoasapi.application.usecase.CadastrarPessoaService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PessoaControllerTest {

    private static final PessoaEmMemoriaRepository PESSOA_REPOSITORY = new PessoaEmMemoriaRepository();

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new PessoaController(new CadastrarPessoaService(PESSOA_REPOSITORY, PESSOA_REPOSITORY)))
            .setControllerAdvice(new RestExceptionHandler())
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
