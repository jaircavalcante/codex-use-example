package com.example.pessoasapi.adapter.config;

import com.example.pessoasapi.application.port.in.CadastrarPessoaUseCase;
import com.example.pessoasapi.application.port.out.BuscarPessoaPorEmailPort;
import com.example.pessoasapi.application.port.out.SalvarPessoaPort;
import com.example.pessoasapi.application.usecase.CadastrarPessoaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    CadastrarPessoaUseCase cadastrarPessoaUseCase(
            BuscarPessoaPorEmailPort buscarPessoaPorEmailPort,
            SalvarPessoaPort salvarPessoaPort
    ) {
        return new CadastrarPessoaService(buscarPessoaPorEmailPort, salvarPessoaPort);
    }
}
